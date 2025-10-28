package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.activities.ActivitiesGetDto;
import az.techvibeds.specialservice.models.Assignee;

import java.util.List;

public interface ActivityService {
    List<ActivitiesGetDto> findAllByAssigneeId(Assignee assignee);
}
