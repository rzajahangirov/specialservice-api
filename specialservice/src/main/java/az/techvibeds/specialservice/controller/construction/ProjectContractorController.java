package az.techvibeds.specialservice.controller.construction;

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
    public ResponseEntity<ProjectContractorReadDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectContractorReadDto>> getAll() {
        return ResponseEntity.ok(contractorService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectContractorReadDto> update(
            @PathVariable Long id,
            @RequestBody ProjectContractorUpdateDto dto
    ) {
        return ResponseEntity.ok(contractorService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contractorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

