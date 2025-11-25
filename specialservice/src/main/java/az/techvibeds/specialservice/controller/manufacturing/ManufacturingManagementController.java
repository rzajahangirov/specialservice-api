package az.techvibeds.specialservice.controller.manufacturing;

import az.techvibeds.specialservice.dtos.manufacturing.ManufacturingManagementDto;
import az.techvibeds.specialservice.services.ManufacturingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manufacturing")
public class ManufacturingManagementController {
    private final ManufacturingService manufacturingService;

    @GetMapping
    public ManufacturingManagementDto getManufacturingManagement(Principal principal) {
        ManufacturingManagementDto manufacturingManagementDto = manufacturingService.getDatasForManufacturingPage(principal.getName());
        return manufacturingManagementDto;
    }
}
