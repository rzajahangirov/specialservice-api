package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.companystock.CompanyStockInventoryDto;
import az.techvibeds.specialservice.dtos.inventory.InventoryDto;
import az.techvibeds.specialservice.dtos.inventory.ProductStatusDto;
import az.techvibeds.specialservice.dtos.product.ProductInventoryDto;
import az.techvibeds.specialservice.enums.ProductStatus;
import az.techvibeds.specialservice.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final CompanyService companyService;
    private final CompanyStockService companyStockService;
    private final ProductService productService;
    private final WarehouseActivityService warehouseActivityService;

    @Override
    public InventoryDto getInventory(String userEmail) {
        Long companyId = companyService.findByUserEmail(userEmail).getId();

        CompanyStockInventoryDto stockInventoryDto = companyStockService.findStockCount(companyId);
        Integer productCount = productService.getProductCountByCompany(companyId);
        String lastTransfer = warehouseActivityService.getLastTransfer(companyId);
        List<ProductInventoryDto> productInventoryDtoList = productService.getProductsByCompanyId(companyId);

        return InventoryDto.builder()
                .stockCount(stockInventoryDto.getStockCount())
                .growthRate(stockInventoryDto.getGrowthRate() + "%")
                .productCount(productCount)
                .lastTransfer(lastTransfer)
                .productInventoryDtoList(productInventoryDtoList)
                .build();
    }

    @Override
    public ProductStatusDto getProductStatuses() {
        ProductStatusDto productStatusDto = new ProductStatusDto();
        productStatusDto.setProductStatus(
                Arrays.stream(ProductStatus.values())
                        .map(Enum ::name)
                        .toList()
        );
        return productStatusDto;
    }
}
