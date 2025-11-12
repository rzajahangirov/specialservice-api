package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.warehouse.WarehouseCreateDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseReadDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseUpdateDto;
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
        return warehouseRepository.findByCompany_Id(companyId);
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
    public WarehouseReadDto update(WarehouseUpdateDto dto) {
        Warehouse warehouse = warehouseRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        warehouse.setName(dto.getName());
        warehouse.setAddress(dto.getAddress());
        warehouseRepository.save(warehouse);
        return mapToReadDto(warehouse);
    }

    @Override
    public void delete(Long id) {
        warehouseRepository.deleteById(id);
    }

    @Override
    public WarehouseReadDto getById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
        return mapToReadDto(warehouse);
    }

    @Override
    public List<WarehouseReadDto> getAll() {
        return warehouseRepository.findAll()
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

