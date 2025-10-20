package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCompany_Id(Long companyId);

    boolean existsByProductCode(String stringCellValue);

    Product findByProductCode(String productCode);
}
