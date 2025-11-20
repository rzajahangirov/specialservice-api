package az.techvibeds.specialservice.controller.construction;

import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseReadDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseUpdateDto;
import az.techvibeds.specialservice.services.ProjectExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-expenses")
@RequiredArgsConstructor
public class ProjectExpenseController {

    private final ProjectExpenseService projectExpenseService;

    @PostMapping
    public ResponseEntity<ProjectExpenseReadDto> create(@RequestBody ProjectExpenseCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectExpenseService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectExpenseReadDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(projectExpenseService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectExpenseReadDto>> getAll() {
        return ResponseEntity.ok(projectExpenseService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectExpenseReadDto> update(
            @PathVariable Long id,
            @RequestBody ProjectExpenseUpdateDto dto) {
        return ResponseEntity.ok(projectExpenseService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectExpenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

