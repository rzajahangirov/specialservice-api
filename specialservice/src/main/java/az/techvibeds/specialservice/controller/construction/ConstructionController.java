package az.techvibeds.specialservice.controller.construction;


import az.techvibeds.specialservice.dtos.construction.ConstructionDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectCreateDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectReadDto;
import az.techvibeds.specialservice.services.ConstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/construction")
public class ConstructionController {
    private final ConstructionService constructionService;

    @GetMapping
    public ResponseEntity<ConstructionDto> construction(Principal principal) {
        ConstructionDto constructionDto = constructionService.getConstructionPageData(principal.getName());
        return ResponseEntity.ok(constructionDto);
    }
    @PostMapping
    public ResponseEntity<ConstructionProjectReadDto> constructionProject(Principal principal, ConstructionProjectCreateDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(constructionService.createConstructionProject(principal.getName(), dto));
    }
}
