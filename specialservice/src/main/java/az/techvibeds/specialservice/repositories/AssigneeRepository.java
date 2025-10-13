package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepository extends JpaRepository<Assignee, Long> {
}
