package az.techvibeds.specialservice.controller;


import az.techvibeds.specialservice.dtos.construction.ConstructionDto;
import az.techvibeds.specialservice.services.ConstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/construction")
public class ConstructionController {
    private final ConstructionService constructionService;

//    @GetMapping
//    public ResponseEntity<ConstructionDto> construction(Principal principal) {
//        ConstructionDto constructionDto = constructionService.getConstructionPageData(principal.getName());
//    }
}
