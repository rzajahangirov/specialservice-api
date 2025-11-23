package az.techvibeds.specialservice.controller.construction;

import az.techvibeds.specialservice.dtos.projectstage.ProjectStageCreateDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageReadDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageUpdateDto;
import az.techvibeds.specialservice.services.ProjectStageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/project-stages")
@RequiredArgsConstructor
public class ProjectStageController {

    private final ProjectStageService projectStageService;

    @PostMapping("/create/{projectId}")
    public ProjectStageReadDto create(
            @PathVariable Long projectId,
            @RequestBody ProjectStageCreateDto dto
    ) {
        return projectStageService.create(projectId, dto);
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectStageReadDto> getAllByProject(@PathVariable Long projectId, Principal principal) {
        return projectStageService.getAllByProject(projectId, principal.getName());
    }

    @GetMapping("/{id}")
    public ProjectStageReadDto getById(@PathVariable Long id, Principal principal) {
        return projectStageService.getById(id, principal.getName());
    }

    @PutMapping("/{id}")
    public ProjectStageReadDto update(
            @PathVariable Long id,
            @RequestBody ProjectStageUpdateDto dto,
            Principal principal
    ) {
        return projectStageService.update(id, dto, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        projectStageService.delete(id ,principal.getName());
    }
}

