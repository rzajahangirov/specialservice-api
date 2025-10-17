package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.enums.ActivityTypeWarehouse;
import az.techvibeds.specialservice.models.WarehouseActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WarehouseActivityRepository extends JpaRepository<WarehouseActivity, Long> {
    @Query("""
    SELECT w 
    FROM WarehouseActivity w 
    WHERE w.type = :type AND w.company.id = :companyId 
    ORDER BY w.date DESC
""")
    WarehouseActivity findTopByTypeAndCompanyIdOrderByDateDesc(@Param("type") ActivityTypeWarehouse type,
                                                               @Param("companyId") Long companyId);

}
