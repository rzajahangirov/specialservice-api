package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.activities.ActivitiesGetDto;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.repositories.ActivityRepository;
import az.techvibeds.specialservice.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ModelMapper modelMapper;
    private final ActivityRepository activityRepository;

    @Override
    public List<ActivitiesGetDto> findAllByAssigneeId(Assignee assignee) {
        return activityRepository.findAllByAssignee(assignee)
                .stream()
                .map(activity -> {
                    ActivitiesGetDto activitiesGetDto = modelMapper.map(activity, ActivitiesGetDto.class);
                    activitiesGetDto.setActivityStatusDto(activity.getActivityStatus().toString() != null ? activity.getActivityStatus().toString() : "");
                    return activitiesGetDto;
                })
                .collect(Collectors.toList());

    }
}
