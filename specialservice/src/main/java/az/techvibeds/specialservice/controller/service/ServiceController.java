package az.techvibeds.specialservice.controller.service;


import az.techvibeds.specialservice.dtos.service.*;
import az.techvibeds.specialservice.payloads.ApiResponse;
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
    @PutMapping
    public ResponseEntity<ServiceReadDto> updateService(@RequestBody ServiceUpdateDto dto, Principal principal) {
        return ResponseEntity.ok(serviceService.updateService(dto, principal.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteService(@PathVariable Long id, Principal principal) {
        serviceService.delete(id, principal.getName());
        return ResponseEntity.ok(new ApiResponse("Service deleted", true));
    }

}
