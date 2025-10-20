package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Product;
import az.techvibeds.specialservice.models.Warehouse;
import az.techvibeds.specialservice.models.WarehouseProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Long> {
    WarehouseProduct findByProductAndWarehouse(Product product, Warehouse warehouse);
}
