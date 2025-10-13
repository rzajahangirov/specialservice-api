package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, Long> {
}
