package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.assignee.AssigneeCreateDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeDetailDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeReadDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeUpdateDto;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.payloads.ApiResponse;
import az.techvibeds.specialservice.services.ActivityService;
import az.techvibeds.specialservice.services.AssigneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignee")
public class AssigneeController {

    private final AssigneeService assigneeService;
    private final ActivityService activityService;


    @PostMapping
    public ResponseEntity<AssigneeReadDto> create(@RequestBody AssigneeCreateDto dto, Principal principal){
        return ResponseEntity.ok(assigneeService.create(dto, principal));
    }

    @PutMapping
    public ResponseEntity<AssigneeReadDto> update(@RequestBody AssigneeUpdateDto dto){
        return ResponseEntity.ok(assigneeService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        assigneeService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Deleted", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssigneeDetailDto> getAssigneeDetails(@PathVariable Long id) {
        AssigneeDetailDto dto = assigneeService.findAssigneeByIdDetailDto(id);
        Assignee assignee = assigneeService.findAssigneeById(id);
        dto.setActivitiesDto(activityService.findAllByAssigneeId(assignee));
        return ResponseEntity.ok(dto);
    }
    @GetMapping
    public ResponseEntity<List<AssigneeReadDto>> getAll(){
        return ResponseEntity.ok(assigneeService.getAll());
    }
}

