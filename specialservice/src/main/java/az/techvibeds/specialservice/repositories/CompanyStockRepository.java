package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.CompanyStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyStockRepository extends JpaRepository<CompanyStock, Long> {
}
