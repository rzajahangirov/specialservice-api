package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.assignee.AssigneeServiceDto;
import az.techvibeds.specialservice.models.Company;

import java.util.List;

public interface AssigneeService {
    List<AssigneeServiceDto> getAssigneeByCompany(Company company);
}
