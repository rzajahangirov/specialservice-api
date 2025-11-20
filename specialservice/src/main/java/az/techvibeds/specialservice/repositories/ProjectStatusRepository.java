package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectStatusRepository extends JpaRepository<ProjectStatus, Long> {
}
