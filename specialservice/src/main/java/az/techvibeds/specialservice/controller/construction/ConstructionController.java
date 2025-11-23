package az.techvibeds.specialservice.controller.construction;


import az.techvibeds.specialservice.dtos.construction.ConstructionDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectCreateDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectReadDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectUpdateDto;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import az.techvibeds.specialservice.services.ConstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/construction")
public class ConstructionController {
    private final ConstructionService constructionService;
    private final ConstructionProjectService constructionProjectService;

    @GetMapping
    public ResponseEntity<ConstructionDto> construction(Principal principal) {
        ConstructionDto constructionDto = constructionService.getConstructionPageData(principal.getName());
        return ResponseEntity.ok(constructionDto);
    }
    @PostMapping
    public ResponseEntity<ConstructionProjectReadDto> constructionProject(Principal principal, @RequestBody ConstructionProjectCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(constructionService.createConstructionProject(principal.getName(), dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ConstructionProjectReadDto> updateConstructionProject(
            @PathVariable Long id,
            @RequestBody ConstructionProjectUpdateDto dto,
            Principal principal) {

        ConstructionProjectReadDto updatedProject = constructionService.updateConstructionProject(id, dto, principal.getName());
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConstructionProject(@PathVariable Long id, Principal principal) {
        constructionService.deleteConstructionProject(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filter")
    public ResponseEntity<List<ConstructionProjectDto>> filterConstructionProject(@RequestParam(required = false) Long statusId,
                                                                                  @RequestParam(required = false) String projectName,
                                                                                  Principal principal) {
        List<ConstructionProjectDto> constructionProjectDtoList = constructionProjectService.getFilteredProjects(statusId, projectName, principal.getName());
        return ResponseEntity.ok(constructionProjectDtoList);
    }
}
