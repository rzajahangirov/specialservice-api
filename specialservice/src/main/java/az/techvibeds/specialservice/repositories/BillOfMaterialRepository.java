package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.enums.BomStatus;
import az.techvibeds.specialservice.models.BillOfMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillOfMaterialRepository extends JpaRepository<BillOfMaterial, Long> {
    List<BillOfMaterial> findAllByCompany_Id(Long companyId);

    List<BillOfMaterial> findAllByCompany_IdAndStatus(Long companyId, BomStatus status);
    @Query("SELECT b FROM BillOfMaterial b " +
            "WHERE b.company.id = :companyId " +
            "AND (CAST(b.id AS string) LIKE CONCAT('%', :keyword, '%') " +
            "OR LOWER(b.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<BillOfMaterial> searchByIdOrName(@Param("keyword") String keyword,
                                          @Param("companyId") Long companyId);


}
