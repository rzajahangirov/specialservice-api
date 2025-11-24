package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.enums.ProductionOrderStatus;
import az.techvibeds.specialservice.models.ProductionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductionOrderRepository extends JpaRepository<ProductionOrder, Long> {

    List<ProductionOrder> findAllByCompany_Id(Long companyId);

    List<ProductionOrder> findAllByCompany_IdAndStatus(Long id, ProductionOrderStatus status);
    @Query("SELECT po FROM ProductionOrder po " +
            "JOIN po.manufacturedProduct mp " +
            "WHERE po.company.id = :companyId " +
            "AND (CAST(po.id AS string) = :keyword OR LOWER(mp.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<ProductionOrder> searchByKeywordAndCompany(@Param("keyword") String keyword,
                                                    @Param("companyId") Long companyId);


}
