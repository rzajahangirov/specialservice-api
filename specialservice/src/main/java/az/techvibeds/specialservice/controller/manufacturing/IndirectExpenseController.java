package az.techvibeds.specialservice.controller.manufacturing;

import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseReadDto;
import az.techvibeds.specialservice.dtos.indirectexpense.IndirectExpenseUpdateDto;
import az.techvibeds.specialservice.services.IndirectExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/indirect-expense")
public class IndirectExpenseController {
    private final IndirectExpenseService indirectExpenseService;

    @PostMapping
    public ResponseEntity<IndirectExpenseReadDto> create(
            @RequestBody IndirectExpenseCreateDto dto,
            Principal principal
    ) {
        return ResponseEntity.ok(indirectExpenseService.create(dto, principal.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndirectExpenseReadDto> update(
            @PathVariable Long id,
            @RequestBody IndirectExpenseUpdateDto dto,
            Principal principal
    ) {
        return ResponseEntity.ok(indirectExpenseService.update(id, dto, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Principal principal
    ) {
        indirectExpenseService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<IndirectExpenseReadDto>> getAll(Principal principal) {
        return ResponseEntity.ok(indirectExpenseService.getAll(principal.getName()));
    }
    @GetMapping("/filter")
    public ResponseEntity<List<IndirectExpenseReadDto>> filterByCategory(
            @RequestParam String category,
            Principal principal
    ) {
        return ResponseEntity.ok(
                indirectExpenseService.filterByCategory(category, principal.getName())
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<IndirectExpenseReadDto>> search(
            @RequestParam String keyword,
            Principal principal
    ) {
        return ResponseEntity.ok(
                indirectExpenseService.search(keyword, principal.getName())
        );
    }

}
