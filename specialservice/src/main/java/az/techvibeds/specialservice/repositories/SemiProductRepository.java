package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.enums.SemiProductStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.SemiProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SemiProductRepository extends JpaRepository<SemiProduct, Long> {
    List<SemiProduct> findAllByCompany(Company company);

    List<SemiProduct> findAllByCompany_IdAndStatus(Long id, SemiProductStatus semiProductStatus);
    @Query("SELECT sp FROM SemiProduct sp " +
            "WHERE sp.company.id = :companyId " +
            "AND (CAST(sp.id AS string) = :keyword " +
            "OR LOWER(sp.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<SemiProduct> searchByKeywordAndCompany(@Param("keyword") String keyword,
                                                @Param("companyId") Long companyId);
}
