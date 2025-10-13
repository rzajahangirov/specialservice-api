package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
