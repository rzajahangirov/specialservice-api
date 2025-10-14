package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
