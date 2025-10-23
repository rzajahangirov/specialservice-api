package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.service.ServiceDescriptionDto;
import az.techvibeds.specialservice.dtos.service.ServiceExecutionStatusDto;
import az.techvibeds.specialservice.enums.ServiceStatus;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.Service;
import az.techvibeds.specialservice.repositories.ServiceRepository;
import az.techvibeds.specialservice.services.ServiceService;
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

    @Override
    public Map<String, Integer> getCountOfServicesByCompany(Company byUserEmail) {
        List<Service> serviceList = serviceRepository.findAllByCompany(byUserEmail);
        Integer allCount = serviceList.size();
        Integer inProgressCount = 0;
        Integer completedCount = 0;
        Integer pendingCount = 0;
        for (Service service : serviceList) {
            if (service.getStatus() == ServiceStatus.IN_PROGRESS) {
                inProgressCount++;
            }else if (service.getStatus() == ServiceStatus.COMPLETED) {
                completedCount++;
            }else if (service.getStatus() == ServiceStatus.PENDING) {
                pendingCount++;
            }
        }
        Map<String,Integer> countOfServices = new HashMap<>();
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

}
