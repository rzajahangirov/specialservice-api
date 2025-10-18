package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.companyStock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.CompanyStock;
import az.techvibeds.specialservice.repositories.CompanyStockRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.CompanyStockService;
import az.techvibeds.specialservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyStockServiceImpl implements CompanyStockService {
    private final ModelMapper modelMapper;
    private final CompanyStockRepository companyStockRepository;
    private final CompanyService companyService;
    private final ProductService productService;


    //Cari Stok deyeri ve oten ayin ortalama stock sayisina nisbet faizi
    @Override
    public CompanyStockInventoryDto findStockCount(Long companyId) {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfLastMonth = now.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = now.minusMonths(1).withDayOfMonth(now.minusMonths(1).lengthOfMonth());
        List<CompanyStock> companyStockList = companyStockRepository.findByCompanyIdAndDateBetween(companyId, firstDayOfLastMonth, lastDayOfLastMonth);
        Long monthlyStockAvg = 0L;
        for (CompanyStock companyStock : companyStockList) {
            monthlyStockAvg+=companyStock.getStockCount();
        }
        monthlyStockAvg/=companyStockList.size();
        Long nowStockCount = calculateStockForCompany(companyId);
        CompanyStockInventoryDto companyStockInventoryDto = new CompanyStockInventoryDto();
        companyStockInventoryDto.setStockCount(nowStockCount);

        Double growthRate = ((nowStockCount/monthlyStockAvg)-1.0)*100;
        String formatted = String.format("%.2f", growthRate);

        companyStockInventoryDto.setGrowthRate(formatted);
        return companyStockInventoryDto;
    }


    //gundelik sirketin umumi stokunu yadda saxlayir
    @Scheduled(cron = "0 0 0 * * ?")
    public void fillDailyStock() {
        List<Company> companies = companyService.findAll();

        for (Company company : companies) {
            CompanyStock stock = new CompanyStock();
            stock.setCompany(company);
            stock.setDate(LocalDate.now());


            Long calculatedStock = calculateStockForCompany(company.getId());
            stock.setStockCount(calculatedStock);

            companyStockRepository.save(stock);
        }
    }

    private Long calculateStockForCompany(Long companyId) {
        return productService.calculateCompanyStock(companyId);
    }


}
