package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.models.WarehouseProduct;

import java.util.List;

public interface WarehouseService {

    Warehouse getWarehouseByName(String stringCellValue);

    List<WarehouseProduct> findAllByCompany_Id(Long companyId);

    Warehouse getWarehouseByNameAndCompanyId(String warehouseName, Company company);
}
