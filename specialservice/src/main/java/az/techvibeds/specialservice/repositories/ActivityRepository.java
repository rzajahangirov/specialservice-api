package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
