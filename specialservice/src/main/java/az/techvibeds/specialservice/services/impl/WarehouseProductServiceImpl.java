package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.WarehouseProduct;
import az.techvibeds.specialservice.repositories.WarehouseProductRepository;
import az.techvibeds.specialservice.services.WarehouseProductService;
import az.techvibeds.specialservice.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseProductServiceImpl implements WarehouseProductService {
    private final WarehouseProductRepository warehouseProductRepository;
    private final WarehouseService warehouseService;

    @Override
    public WarehouseProduct createWarehouseProduct(Product product, String stringCellValue, double numericCellValue) {
        WarehouseProduct warehouseProduct = new WarehouseProduct();
        warehouseProduct.setProduct(product);
        warehouseProduct.setQuantity((int)numericCellValue);
        warehouseProduct.setWarehouse(warehouseService.getWarehouseByName(stringCellValue));
        warehouseProductRepository.save(warehouseProduct);
        return warehouseProduct;
    }
}
