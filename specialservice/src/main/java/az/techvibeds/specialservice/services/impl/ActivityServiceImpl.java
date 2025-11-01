package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.activities.ActivitiesReadDto;
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
    public List<ActivitiesReadDto> findAllByAssigneeId(Assignee assignee) {
        return activityRepository.findAllByAssignee(assignee)
                .stream()
                .map(activity -> {
                    ActivitiesReadDto activitiesGetDto = modelMapper.map(activity, ActivitiesReadDto.class);
                    activitiesGetDto.setActivityStatusDto(activity.getActivityStatus().toString() != null ? activity.getActivityStatus().toString() : "");
                    return activitiesGetDto;
                })
                .collect(Collectors.toList());

    }
}
