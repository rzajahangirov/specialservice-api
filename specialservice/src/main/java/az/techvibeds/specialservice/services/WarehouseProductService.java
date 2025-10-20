package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.WarehouseProduct;

public interface WarehouseProductService {
    WarehouseProduct createWarehouseProduct(Product product, String warehouseName, double quantity);

    void removeFromWarehouse(String productCode, String warehouseName, Integer quantity, Company byUserEmail) throws Exception;

    void transferProductBetweenWarehouses(String productCode, String fromWarehouse, String toWarehouse, int quantity) throws Exception;
}
