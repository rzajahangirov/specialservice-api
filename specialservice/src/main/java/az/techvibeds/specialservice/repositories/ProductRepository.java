package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCompany_Id(Long companyId);

    boolean existsByProductCode(String stringCellValue);

    Product findByProductCode(String productCode);

    @Query("SELECT p FROM Product p WHERE p.company.id = :companyId " +
            "AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.productCode) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> searchProductsByCompanyAndKeyword(@Param("companyId") Long companyId,
                                                    @Param("keyword") String keyword);
}
