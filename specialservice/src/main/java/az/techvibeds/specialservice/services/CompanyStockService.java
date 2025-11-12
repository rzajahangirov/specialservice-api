package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.companystock.CompanyStockInventoryDto;

public interface CompanyStockService {
    CompanyStockInventoryDto findStockCount(Long id);
}
