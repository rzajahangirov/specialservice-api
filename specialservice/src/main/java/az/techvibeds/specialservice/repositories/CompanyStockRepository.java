package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.CompanyStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CompanyStockRepository extends JpaRepository<CompanyStock, Long> {
    List<CompanyStock> findByCompanyIdAndDateBetween(Long companyId, LocalDate startDate, LocalDate endDate);
}
