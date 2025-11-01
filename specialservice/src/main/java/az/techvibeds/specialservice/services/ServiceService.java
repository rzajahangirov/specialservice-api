package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.service.*;
import az.techvibeds.specialservice.models.Company;

import java.util.List;
import java.util.Map;

public interface ServiceService {
    Map<String, Integer> getCountOfServicesByCompany(Company byUserEmail);

    List<ServiceDescriptionDto> getServiceDescriptions(Company company);

    List<ServiceExecutionStatusDto> getServiceExecutionStatus(Company company);

    ServiceReadDto createService(String email, ServiceCreateDto serviceCreateDto);

    ServiceGetDataDto getCompanyServicesData(String email);
}
