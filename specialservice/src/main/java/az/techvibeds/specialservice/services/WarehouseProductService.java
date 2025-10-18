package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.WarehouseProduct;

public interface WarehouseProductService {
    WarehouseProduct createWarehouseProduct(Product product, String stringCellValue, double numericCellValue);
}
