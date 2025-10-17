package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.companyStock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.repositories.CompanyStockRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.CompanyStockService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CompanyStockServiceImpl implements CompanyStockService {
    private final ModelMapper modelMapper;
    private final CompanyStockRepository companyStockRepository;
    private final CompanyService companyService;
//    @Override
//    public CompanyStockInventoryDto findStockCount(String name) {
//        Company company = companyService.findByUserEmail(name);
//        LocalDate currentDate = LocalDate.now();
//
//        return null;
//    }
}
