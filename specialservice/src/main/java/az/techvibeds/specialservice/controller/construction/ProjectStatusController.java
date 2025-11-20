package az.techvibeds.specialservice.controller.construction;


import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusCreateDto;
import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusReadDto;
import az.techvibeds.specialservice.dtos.projectstatus.ProjectStatusUpdateDto;
import az.techvibeds.specialservice.services.ProjectStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project-status")
public class ProjectStatusController {
    private final ProjectStatusService projectStatusService;

    @PostMapping
    public ResponseEntity<ProjectStatusReadDto> create(@RequestBody ProjectStatusCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectStatusService.createProjectStatus(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectStatusReadDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectStatusService.getProjectStatusById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectStatusReadDto>> getAll() {
        return ResponseEntity.ok(projectStatusService.getAllProjectStatuses());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectStatusReadDto> update(
            @PathVariable Long id,
            @RequestBody ProjectStatusUpdateDto dto
    ) {
        return ResponseEntity.ok(projectStatusService.updateProjectStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectStatusService.deleteProjectStatus(id);
        return ResponseEntity.noContent().build();
    }

}
