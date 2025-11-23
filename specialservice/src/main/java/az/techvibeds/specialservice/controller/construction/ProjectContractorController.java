package az.techvibeds.specialservice.controller.construction;

import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorReadDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorUpdateDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorCreateDto;
import az.techvibeds.specialservice.services.ProjectContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/project-contractors")
@RequiredArgsConstructor
public class ProjectContractorController {

    private final ProjectContractorService contractorService;

    @PostMapping
    public ResponseEntity<ProjectContractorReadDto> create(
            @RequestBody ProjectContractorCreateDto dto,
            Principal principal
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contractorService.create(dto, principal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectContractorReadDto> getById(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(contractorService.getById(id, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<ProjectContractorReadDto>> getAll(Principal principal) {
        return ResponseEntity.ok(contractorService.getAll(principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectContractorReadDto> update(
            @PathVariable Long id,
            @RequestBody ProjectContractorUpdateDto dto,
            Principal principal
    ) {
        return ResponseEntity.ok(contractorService.update(id, dto, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        contractorService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filter")
    public ResponseEntity<List<ProjectContractorDto>> filter(@RequestParam(required = false) String contractorName, Principal principal) {
        List<ProjectContractorDto> projectContractorDtoList = contractorService.getFilteredContractors(contractorName, principal.getName());
        return ResponseEntity.ok(projectContractorDtoList);
    }
}

