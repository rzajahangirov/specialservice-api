package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.assignee.AssigneeDetailDto;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.services.ActivityService;
import az.techvibeds.specialservice.services.AssigneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignees")
public class AssigneeController {

    private final AssigneeService assigneeService;
    private final ActivityService activityService;

    @GetMapping("/{id}")
    public ResponseEntity<AssigneeDetailDto> getAssigneeDetails(@PathVariable Long id) {
        AssigneeDetailDto dto = assigneeService.findAssigneeByIdDetailDto(id);
        Assignee assignee = assigneeService.findAssigneeById(id);
        dto.setActivitiesDto(activityService.findAllByAssigneeId(assignee));
        return ResponseEntity.ok(dto);
    }
}

