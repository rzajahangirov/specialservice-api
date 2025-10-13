package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
