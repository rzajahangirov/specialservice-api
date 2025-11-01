package az.techvibeds.specialservice.controller;


import az.techvibeds.specialservice.dtos.service.*;
import az.techvibeds.specialservice.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceService serviceService;


    @GetMapping
    public ResponseEntity<ServiceGetDataDto> getServices(Principal principal) {
        return ResponseEntity.ok(serviceService.getCompanyServicesData(principal.getName()));
    }


    @PostMapping
    public ResponseEntity<ServiceReadDto> createService(Principal principal,
                                                        @RequestBody ServiceCreateDto dto) {
        return ResponseEntity.ok(serviceService.createService(principal.getName(), dto));
    }
}
