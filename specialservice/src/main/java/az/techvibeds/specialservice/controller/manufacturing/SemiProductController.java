package az.techvibeds.specialservice.controller.manufacturing;

import az.techvibeds.specialservice.dtos.semiproduct.SemiProductCreateDto;
import az.techvibeds.specialservice.dtos.semiproduct.SemiProductReadDto;
import az.techvibeds.specialservice.dtos.semiproduct.SemiProductUpdateDto;
import az.techvibeds.specialservice.services.SemiProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/semi-product")
public class SemiProductController {
    private final SemiProductService semiProductService;

    @PostMapping
    public ResponseEntity<SemiProductReadDto> create(
            @RequestBody SemiProductCreateDto dto,
            Principal principal
    ) {
        return ResponseEntity.ok(
                semiProductService.create(dto, principal.getName())
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemiProductReadDto> update(
            @PathVariable Long id,
            @RequestBody SemiProductUpdateDto dto,
            Principal principal
    ) {
        return ResponseEntity.ok(
                semiProductService.update(id, dto, principal.getName())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            Principal principal
    ) {
        semiProductService.delete(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SemiProductReadDto>> getAll(Principal principal) {
        return ResponseEntity.ok(
                semiProductService.getAll(principal.getName())
        );
    }
    @GetMapping("/filter")
    public ResponseEntity<List<SemiProductReadDto>> filterByStatus(
            @RequestParam(required = false) String status,
            Principal principal
    ) {
        List<SemiProductReadDto> result = semiProductService.filterByStatus(status, principal.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SemiProductReadDto>> searchByNameOrId(
            @RequestParam(required = false) String keyword,
            Principal principal
    ) {
        List<SemiProductReadDto> result = semiProductService.search(keyword, principal.getName());
        return ResponseEntity.ok(result);
    }

}
