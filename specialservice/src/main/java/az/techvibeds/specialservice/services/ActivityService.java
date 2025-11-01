package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.activities.ActivitiesReadDto;
import az.techvibeds.specialservice.models.Assignee;

import java.util.List;

public interface ActivityService {
    List<ActivitiesReadDto> findAllByAssigneeId(Assignee assignee);
}
