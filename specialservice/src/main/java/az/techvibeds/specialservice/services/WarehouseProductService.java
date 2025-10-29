package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.WarehouseProduct;

public interface WarehouseProductService {
    WarehouseProduct createWarehouseProduct(Product product, Long warehouseId, double quantity);

    void removeFromWarehouse(String productCode, Long warehouseId, Integer quantity) throws Exception;

    void transferProductBetweenWarehouses(String productCode, Long fromWarehouseId, Long toWarehouseId, int quantity) throws Exception;

    Integer updateInventoryWarehouseProduct(WarehouseProduct warehouseProduct, Long warehouseId, Integer stock);

    WarehouseProduct findWarehouseProductById(Long id);

    void deleteWarehouseProduct(Long id);

    WarehouseProduct createWarehouseProductFromExcel(Product product, String warehouseName, double quantity);
}
