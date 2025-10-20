package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.models.WarehouseProduct;
import az.techvibeds.specialservice.repositories.WarehouseRepository;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private final ModelMapper modelMapper;
    private final WarehouseRepository warehouseRepository;



    @Override
    public Warehouse getWarehouseByName(String stringCellValue) {
        return warehouseRepository.findByName(stringCellValue);
    }

    @Override
    public List<WarehouseProduct> findAllByCompany_Id(Long companyId) {
        return warehouseRepository.findByCompany_Id(companyId);
    }

    @Override
    public Warehouse getWarehouseByNameAndCompanyId(String warehouseName, Company company) {
        return warehouseRepository.findByNameAndCompany(warehouseName, company);
    }
}
