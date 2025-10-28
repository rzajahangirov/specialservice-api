package az.techvibeds.specialservice.repositories;

import az.techvibeds.specialservice.dtos.activities.ActivitiesGetDto;
import az.techvibeds.specialservice.models.Activity;
import az.techvibeds.specialservice.models.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findAllByAssignee(Assignee assignee);
}
