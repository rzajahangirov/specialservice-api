package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findByUsers_Id(Long userId);
}
