package az.techvibeds.specialservice.controller;

import az.techvibeds.specialservice.dtos.assignee.AssigneeServiceDto;
import az.techvibeds.specialservice.dtos.service.ServiceExecutionStatusDto;
import az.techvibeds.specialservice.dtos.service.ServiceDescriptionDto;
import az.techvibeds.specialservice.dtos.service.ServiceGetDataDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.services.AssigneeService;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/service")
public class ServiceController {
    private final ServiceService serviceService;
    private final CompanyService companyService;
    private final AssigneeService assigneeService;

    @GetMapping
    public ResponseEntity<ServiceGetDataDto> getServices(Principal principal) {
        Company company = companyService.findByUserEmail(principal.getName());
        ServiceGetDataDto serviceGetDataDto = new ServiceGetDataDto();

        //Xidmet Idareetme Umumi Baxis
        Map<String,Integer> serviceCounts = serviceService.getCountOfServicesByCompany(company);
        serviceGetDataDto.setServiceCounts(serviceCounts);

        //Xidmet Tipleri ve Qiymetler
        List<ServiceDescriptionDto> serviceDescriptionDto = serviceService.getServiceDescriptions(company);
        serviceGetDataDto.setServiceDescriptionDtoList(serviceDescriptionDto);

        //Icra veziyyetini izlenmesi
        List<ServiceExecutionStatusDto> serviceExecutionStatusDtoList = serviceService.getServiceExecutionStatus(company);
        serviceGetDataDto.setServiceExecutionStatusDtoList(serviceExecutionStatusDtoList);

        //Isci teyinatlarina baxis
        List<AssigneeServiceDto> assigneeServiceDtoList = assigneeService.getAssigneeByCompany(company);
        serviceGetDataDto.setAssigneeServiceDtoList(assigneeServiceDtoList);



        return ResponseEntity.ok(serviceGetDataDto);
    }
}
