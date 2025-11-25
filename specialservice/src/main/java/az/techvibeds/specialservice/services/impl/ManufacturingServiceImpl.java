package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.manufacturing.ManufacturingManagementDto;
import az.techvibeds.specialservice.enums.ProductionOrderStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.ProductionOrder;
import az.techvibeds.specialservice.repositories.ProductionOrderRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ManufacturingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturingServiceImpl implements ManufacturingService {

    private final ProductionOrderRepository productionOrderRepository;
    private final CompanyService companyService;

    @Override
    public ManufacturingManagementDto getDatasForManufacturingPage(String userEmail) {
        ManufacturingManagementDto dto = new ManufacturingManagementDto();
        Company company = companyService.findByUserEmail(userEmail);
        List<ProductionOrder> productionOrder = productionOrderRepository.findAllByCompany_Id(company.getId());
        Integer productionOrderCount = 0;
        Integer newProductionOrderCount = 0;
        Instant now = Instant.now();
        Instant twentyFourHoursAgo = now.minus(24, ChronoUnit.HOURS);

        for (ProductionOrder order : productionOrder) {
            if (order.getStatus() == ProductionOrderStatus.IN_PRODUCTION) {
                productionOrderCount++;
            }

            if (order.getStartDate() != null) {
                Instant startInstant = order.getStartDate().toInstant();
                if (startInstant.isAfter(twentyFourHoursAgo)) {
                    newProductionOrderCount++;
                }
            }
        }
        dto.setInProductionOrderCount(productionOrderCount);
        dto.setLast24HoursOrderCount(newProductionOrderCount);
        Integer thisMonthQty = getThisMonthQuantity(company.getId());
        Integer lastMonthQty = getLastMonthQuantity(company.getId());

        dto.setMonthlyProductionCount(thisMonthQty);

        if (thisMonthQty != null && lastMonthQty != null && lastMonthQty > 0) {
            double relative = (double) thisMonthQty / lastMonthQty * 100;
            dto.setRelativeToLastMonth(String.format("%.2f%%", relative));
        } else {
            dto.setRelativeToLastMonth("N/A");
        }

        return dto;
    }
    public Integer getThisMonthQuantity(Long companyId) {
        LocalDate now = LocalDate.now();

        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());

        Date start = java.sql.Date.valueOf(firstDay);
        Date end = java.sql.Date.valueOf(lastDay);

        return productionOrderRepository.getTotalQuantityBetweenDates(companyId, start, end);
    }

    public Integer getLastMonthQuantity(Long companyId) {
        LocalDate now = LocalDate.now().minusMonths(1);

        LocalDate firstDay = now.withDayOfMonth(1);
        LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());

        Date start = java.sql.Date.valueOf(firstDay);
        Date end = java.sql.Date.valueOf(lastDay);

        return productionOrderRepository.getTotalQuantityBetweenDates(companyId, start, end);
    }

}
