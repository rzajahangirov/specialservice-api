package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByCompany(Company company);
}
