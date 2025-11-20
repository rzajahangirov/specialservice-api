package az.techvibeds.specialservice.services.impl;


import az.techvibeds.specialservice.dtos.service.*;
import az.techvibeds.specialservice.enums.ServiceStatus;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Service;
import az.techvibeds.specialservice.repositories.ServiceRepository;
import az.techvibeds.specialservice.services.AssigneeService;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ServiceService;
import az.techvibeds.specialservice.services.UnitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ModelMapper modelMapper;
    private final ServiceRepository serviceRepository;
    private final CompanyService companyService;
    private final AssigneeService assigneeService;
    private final UnitService unitService;

    @Override
    public Map<String, Integer> getCountOfServicesByCompany(Company company) {
        List<Service> serviceList = serviceRepository.findAllByCompany(company);

        int inProgressCount = 0;
        int completedCount = 0;
        int pendingCount = 0;

        for (Service service : serviceList) {
            ServiceStatus status = service.getStatus();
            if (status == ServiceStatus.IN_PROGRESS) {
                inProgressCount++;
            } else if (status == ServiceStatus.COMPLETED) {
                completedCount++;
            } else if (status == ServiceStatus.PENDING) {
                pendingCount++;
            }
        }

        Map<String, Integer> countOfServices = new HashMap<>();
        countOfServices.put("inProgressCount", inProgressCount);
        countOfServices.put("completedCount", completedCount);
        countOfServices.put("pendingCount", pendingCount);
        return countOfServices;
    }

    @Override
    public List<ServiceDescriptionDto> getServiceDescriptions(Company company) {
        return serviceRepository.findAllByCompany(company)
                .stream()
                .map(service -> {
                    ServiceDescriptionDto dto = modelMapper.map(service, ServiceDescriptionDto.class);
                    dto.setDtoUnit(service.getUnit() != null ? service.getUnit().getName() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ServiceExecutionStatusDto> getServiceExecutionStatus(Company company) {
        return serviceRepository.findAllByCompany(company)
                .stream()
                .map(service -> {
                    ServiceExecutionStatusDto dto = modelMapper.map(service, ServiceExecutionStatusDto.class);
                    dto.setDtoAssignee(service.getAssignee() != null ? service.getAssignee().getName() : null);
                    dto.setDtoStatus(service.getStatus() != null ? service.getStatus().name() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ServiceReadDto createService(String email, ServiceCreateDto dto) {
        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setAmount(dto.getAmount());
        service.setDeadline(dto.getDeadline());
        if (dto.getStatusDto().toUpperCase().equals("IN_PROGRESS") || dto.getStatusDto().toUpperCase().equals("INPROGRESS")){
            service.setStatus(ServiceStatus.IN_PROGRESS);
        }else if(dto.getStatusDto().toUpperCase().equals("COMPLETED")){
            service.setStatus(ServiceStatus.COMPLETED);
        }else if(dto.getStatusDto().toUpperCase().equals("PENDING")){
            service.setStatus(ServiceStatus.PENDING);
        }else if(dto.getStatusDto().toUpperCase().equals("CANCELLED")){
            service.setStatus(ServiceStatus.CANCELED);
        }
        service.setCompany(companyService.findByUserEmail(email));

        Assignee assignee = assigneeService.findAssigneeById(dto.getAssigneeId());
        if (assignee.getActiveServiceCount() < assignee.getTotalCapacity()) {
            service.setAssignee(assignee);
        } else {
            throw new RuntimeException("Assignee capacity exceeded");
        }

        service.setUnit(unitService.findUnitById(dto.getUnitId()));
        serviceRepository.save(service);

        return mapToReadDto(service);
    }


    private ServiceReadDto mapToReadDto(Service service) {
        ServiceReadDto dto = modelMapper.map(service, ServiceReadDto.class);
        dto.setAssignee(service.getAssignee() != null ? service.getAssignee().getName() : null);
        dto.setUnit(service.getUnit() != null ? service.getUnit().getName() : null);
        dto.setCompanyName(service.getCompany() != null ? service.getCompany().getName() : null);
        return dto;
    }

    @Override
    public ServiceGetDataDto getCompanyServicesData(String email) {
        Company company = companyService.findByUserEmail(email);

        ServiceGetDataDto dto = new ServiceGetDataDto();
        dto.setServiceCounts(getCountOfServicesByCompany(company));
        dto.setServiceDescriptionDtoList(getServiceDescriptions(company));
        dto.setServiceExecutionStatusDtoList(getServiceExecutionStatus(company));
        dto.setAssigneeServiceDtoList(assigneeService.getAssigneeByCompany(company));

        return dto;
    }

    @Override
    public ServiceReadDto updateService(ServiceUpdateDto dto, String userEmail) {
        Service service = serviceRepository.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Service not found"));
        Company company = companyService.findByUserEmail(userEmail);
        if (company == service.getCompany()) {
            if (dto.getName() != null) service.setName(dto.getName());
            if (dto.getDescription() != null) service.setDescription(dto.getDescription());
            if (dto.getAmount() != null) service.setAmount(dto.getAmount());
            if (dto.getDeadline() != null) service.setDeadline(dto.getDeadline());
            if (dto.getStatusDto().toUpperCase().equals("IN_PROGRESS") || dto.getStatusDto().toUpperCase().equals("INPROGRESS")) {
                service.setStatus(ServiceStatus.IN_PROGRESS);
            } else if (dto.getStatusDto().toUpperCase().equals("COMPLETED")) {
                service.setStatus(ServiceStatus.COMPLETED);
            } else if (dto.getStatusDto().toUpperCase().equals("PENDING")) {
                service.setStatus(ServiceStatus.PENDING);
            } else if (dto.getStatusDto().toUpperCase().equals("CANCELLED")) {
                service.setStatus(ServiceStatus.CANCELED);
            }
            if (dto.getUnitId() != null) service.setUnit(unitService.findUnitById(dto.getUnitId()));
            if (dto.getAssigneeId() != null) service.setAssignee(assigneeService.findAssigneeById(dto.getAssigneeId()));

            serviceRepository.save(service);

            return mapToReadDto(service);
        }else {
            throw new RuntimeException("Company does not match");
        }
    }

    @Override
    public void delete(Long id, String userEmail) {
        Company company = companyService.findByUserEmail(userEmail);
        Service service = serviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Service not found"));
        if (company == service.getCompany()) {
            serviceRepository.deleteById(id);
        }else {
            throw new RuntimeException("Company does not match");
        }
    }

}
