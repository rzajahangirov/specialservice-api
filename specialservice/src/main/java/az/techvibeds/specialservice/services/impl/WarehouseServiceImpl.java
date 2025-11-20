package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.warehouse.WarehouseCreateDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseReadDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseUpdateDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.repositories.WarehouseRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final ModelMapper modelMapper;
    private final WarehouseRepository warehouseRepository;
    private final CompanyService companyService;



    @Override
    public Warehouse getWarehouseByName(String warehouseName) {
        return warehouseRepository.findByName(warehouseName);
    }

    @Override
    public List<Warehouse> findAllByCompany_Id(Long companyId) {
        return warehouseRepository.findAllByCompany_Id(companyId);
    }

    @Override
    public WarehouseReadDto create(WarehouseCreateDto dto, String email) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouse.setCompany(companyService.findByUserEmail(email));
        warehouse.setWarehouseProducts(null);
        warehouse.setWarehouseActivity(null);

        warehouseRepository.save(warehouse);
        return mapToReadDto(warehouse);
    }

    @Override
    public WarehouseReadDto update(WarehouseUpdateDto dto, String userEmail) {
        Warehouse warehouse = warehouseRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Company company = companyService.findByUserEmail(userEmail);
        if (company == warehouse.getCompany()) {
            warehouse.setName(dto.getName());
            warehouse.setAddress(dto.getAddress());
            warehouseRepository.save(warehouse);
            return mapToReadDto(warehouse);
        }else {
            throw new RuntimeException("Company does not match");
        }
    }

    @Override
    public void delete(Long id, String userEmail) {
        Warehouse warehouse = findById(id);
        Company company = companyService.findByUserEmail(userEmail);
        if (company == warehouse.getCompany()) {
            warehouseRepository.deleteById(id);
        }else {
            throw new RuntimeException("Company does not match");
        }
    }

    @Override
    public WarehouseReadDto getById(Long id, String userEmail) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        Company company = companyService.findByUserEmail(userEmail);
        if (company == warehouse.getCompany()) {
            return mapToReadDto(warehouse);
        }else {
            throw new RuntimeException("Company does not match");
        }
    }

    @Override
    public List<WarehouseReadDto> getAllByCompanyReadDto(String email) {
        return warehouseRepository.findAllByCompany_Id(companyService.findByUserEmail(email).getId())
                .stream()
                .map(this::mapToReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public Warehouse getWarehouseById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow();
    }

    @Override
    public Warehouse findById(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).orElseThrow();
    }

    private WarehouseReadDto mapToReadDto(Warehouse warehouse) {
        WarehouseReadDto dto = modelMapper.map(warehouse, WarehouseReadDto.class);
        if (warehouse.getCompany() != null) {
            dto.setCompanyName(warehouse.getCompany().getName());
        }
        return dto;
    }
}

