package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialCreateDto;
import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialReadDto;
import az.techvibeds.specialservice.dtos.billofmaterial.BillOfMaterialUpdateDto;
import az.techvibeds.specialservice.enums.BomStatus;
import az.techvibeds.specialservice.models.BillOfMaterial;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.repositories.BillOfMaterialRepository;
import az.techvibeds.specialservice.repositories.ManufacturedProductRepository;
import az.techvibeds.specialservice.services.BillOfMaterialService;
import az.techvibeds.specialservice.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillOfMaterialServiceImpl implements BillOfMaterialService {
    private final BillOfMaterialRepository billOfMaterialRepository;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final ManufacturedProductRepository manufacturedProductRepository;

    @Override
    public List<BillOfMaterialReadDto> getAllByCompany(String userEmail) {
        Company company = companyService.findByUserEmail(userEmail);
        List<BillOfMaterial> billOfMaterials = billOfMaterialRepository.findAllByCompany_Id(company.getId());
        List<BillOfMaterialReadDto> billOfMaterialReadDtoList = new ArrayList<>();
        for (BillOfMaterial billOfMaterial : billOfMaterials) {
            billOfMaterialReadDtoList.add(mapToRead(billOfMaterial));
        }
        return billOfMaterialReadDtoList;
    }

    @Override
    public BillOfMaterialReadDto createBom(BillOfMaterialCreateDto createDto, String userEmail) {
        BillOfMaterial billOfMaterial = new BillOfMaterial();
        billOfMaterial.setCompany(companyService.findByUserEmail(userEmail));
        billOfMaterial.setName(createDto.getName());
        billOfMaterial.setCreationDate(LocalDate.now());
        billOfMaterial.setManufacturedProduct(manufacturedProductRepository
                .findById(createDto.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found")));
        billOfMaterial.setStatus(BomStatus.valueOf(createDto.getBomStatus().toUpperCase()));
        billOfMaterialRepository.save(billOfMaterial);
        return mapToRead(billOfMaterial);
    }

    @Override
    public BillOfMaterialReadDto updateBom(Long id, BillOfMaterialUpdateDto updateDto, String userEmail) {
        BillOfMaterial billOfMaterial = billOfMaterialRepository
                .findById(id)
                .orElseThrow(()-> new RuntimeException("BOM not found"));
        if (billOfMaterial.getCompany() != companyService.findByUserEmail(userEmail)) {
            throw new RuntimeException("Access denied");
        }
        billOfMaterial.setName(updateDto.getName());
        billOfMaterial.setCreationDate(updateDto.getCreationDate());
        billOfMaterial.setManufacturedProduct(manufacturedProductRepository.findById(updateDto.getProductId()).orElseThrow(()-> new RuntimeException("Product not found")));
        billOfMaterial.setStatus(BomStatus.valueOf(updateDto.getBomStatus().toUpperCase()));
        billOfMaterialRepository.save(billOfMaterial);
        return mapToRead(billOfMaterial);
    }

    @Override
    public void deleteById(Long id, String name) {
        BillOfMaterial billOfMaterial = billOfMaterialRepository.findById(id).orElseThrow(()-> new RuntimeException("BOM not found"));
        if (billOfMaterial.getCompany() != companyService.findByUserEmail(name)) {
            throw new RuntimeException("Access denied");
        }
        billOfMaterialRepository.delete(billOfMaterial);
    }

    @Override
    public List<BillOfMaterialReadDto> getAllFilteredByStatus(String status, Principal principal) {
        BomStatus bomStatus = BomStatus.valueOf(status.toUpperCase());
        List<BillOfMaterial> billOfMaterials = billOfMaterialRepository.findAllByCompany_IdAndStatus(companyService.findByUserEmail(principal.getName()).getId(), bomStatus);
        List<BillOfMaterialReadDto> billOfMaterialReadDtoList = new ArrayList<>();
        for (BillOfMaterial billOfMaterial : billOfMaterials) {
            billOfMaterialReadDtoList.add(mapToRead(billOfMaterial));
        }
        return billOfMaterialReadDtoList;
    }

    @Override
    public List<BillOfMaterialReadDto> getAllSearchedBom(String keyword, Principal principal) {
        Company company = companyService.findByUserEmail(principal.getName());

        List<BillOfMaterial> billOfMaterials =
                billOfMaterialRepository.searchByIdOrName(keyword, company.getId());
        List<BillOfMaterialReadDto> billOfMaterialReadDtoList = new ArrayList<>();
        for (BillOfMaterial billOfMaterial : billOfMaterials) {
            billOfMaterialReadDtoList.add(mapToRead(billOfMaterial));
        }
        return billOfMaterialReadDtoList;
    }

    private BillOfMaterialReadDto mapToRead(BillOfMaterial billOfMaterial) {
        BillOfMaterialReadDto dto = modelMapper.map(billOfMaterial, BillOfMaterialReadDto.class);
        dto.setProductName(billOfMaterial.getManufacturedProduct().getName());
        dto.setBomStatus(billOfMaterial.getStatus().name());
        return dto;
    }
}
