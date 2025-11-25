package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.semiproduct.SemiProductCreateDto;
import az.techvibeds.specialservice.dtos.semiproduct.SemiProductReadDto;
import az.techvibeds.specialservice.dtos.semiproduct.SemiProductUpdateDto;
import az.techvibeds.specialservice.enums.SemiProductStatus;
import az.techvibeds.specialservice.enums.UnitType;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.SemiProduct;
import az.techvibeds.specialservice.repositories.SemiProductRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.SemiProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemiProductServiceImpl implements SemiProductService {
    private final SemiProductRepository semiProductRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Override
    public SemiProductReadDto create(SemiProductCreateDto dto, String userEmail) {

        Company company = companyService.findByUserEmail(userEmail);

        SemiProduct sp = modelMapper.map(dto, SemiProduct.class);
        sp.setCompany(company);
        sp.setStatus(SemiProductStatus.valueOf(dto.getProductStatus().toUpperCase()));
        sp.setUnit(UnitType.valueOf(dto.getUnitType().toUpperCase()));

        SemiProduct saved = semiProductRepository.save(sp);

        return mapToRead(saved);
    }

    @Override
    public SemiProductReadDto update(Long id, SemiProductUpdateDto dto, String userEmail) {

        SemiProduct sp = getById(id);

        Company userCompany = companyService.findByUserEmail(userEmail);
        if (!sp.getCompany().getId().equals(userCompany.getId())) {
            throw new RuntimeException("Access denied");
        }

        modelMapper.map(dto, sp);
        sp.setStatus(SemiProductStatus.valueOf(dto.getProductStatus().toUpperCase()));
        sp.setUnit(UnitType.valueOf(dto.getUnitType().toUpperCase()));

        SemiProduct updated = semiProductRepository.save(sp);

        return mapToRead(updated);
    }

    @Override
    public void delete(Long id, String userEmail) {

        SemiProduct sp = getById(id);

        Company userCompany = companyService.findByUserEmail(userEmail);
        if (!sp.getCompany().getId().equals(userCompany.getId())) {
            throw new RuntimeException("Access denied!");
        }

        semiProductRepository.delete(sp);
    }

    @Override
    public List<SemiProductReadDto> getAll(String userEmail) {

        Company company = companyService.findByUserEmail(userEmail);
        List<SemiProduct> list = semiProductRepository.findAllByCompany(company);
        List<SemiProductReadDto> dtos = new ArrayList<>();
        for (SemiProduct sp : list) {
            dtos.add(mapToRead(sp));
        }
        return dtos;
    }

    @Override
    public SemiProduct getById(Long id) {
        return semiProductRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SemiProduct can not find!"));
    }

    @Override
    public List<SemiProductReadDto> filterByStatus(String status, String userEmail) {

        if (status == null || status.isBlank()) {
            throw new RuntimeException("Status can not be empty!");
        }

        SemiProductStatus semiProductStatus;
        try {
            semiProductStatus = SemiProductStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Unknown semi product status: " + status);
        }

        Company company = companyService.findByUserEmail(userEmail);

        List<SemiProduct> semiProducts =
                semiProductRepository.findAllByCompany_IdAndStatus(company.getId(), semiProductStatus);

        return semiProducts.stream()
                .map(sp -> modelMapper.map(sp, SemiProductReadDto.class))
                .toList();
    }

    @Override
    public List<SemiProductReadDto> search(String keyword, String userEmail) {

        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }

        Company company = companyService.findByUserEmail(userEmail);

        List<SemiProduct> semiProducts =
                semiProductRepository.searchByKeywordAndCompany(keyword, company.getId());

        return semiProducts.stream()
                .map(sp -> modelMapper.map(sp, SemiProductReadDto.class))
                .toList();
    }


    private SemiProductReadDto mapToRead(SemiProduct sp) {
        SemiProductReadDto dto = modelMapper.map(sp, SemiProductReadDto.class);
        dto.setProductStatus(sp.getStatus().name());
        dto.setUnitType(sp.getUnit().name());
        return dto;
    }

}
