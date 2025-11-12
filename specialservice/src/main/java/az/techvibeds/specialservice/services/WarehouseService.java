package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.warehouse.WarehouseCreateDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseReadDto;
import az.techvibeds.specialservice.dtos.warehouse.WarehouseUpdateDto;
import az.techvibeds.specialservice.models.Warehouse;

import java.util.List;

public interface WarehouseService {

    Warehouse getWarehouseByName(String warehouseName);

    List<Warehouse> findAllByCompany_Id(Long companyId);

    WarehouseReadDto create(WarehouseCreateDto dto, String email);

    WarehouseReadDto update(WarehouseUpdateDto dto);

    void delete(Long id);

    WarehouseReadDto getById(Long id);

    List<WarehouseReadDto> getAll();

    Warehouse getWarehouseById(Long warehouseId);

    Warehouse findById(Long warehouseId);
}
