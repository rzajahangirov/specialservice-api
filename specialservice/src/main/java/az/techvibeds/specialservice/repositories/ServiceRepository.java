package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
