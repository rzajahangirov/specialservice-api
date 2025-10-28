package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.assignee.AssigneeDetailDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeGetDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeServiceDto;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.models.Company;

import java.util.List;

public interface AssigneeService {
    List<AssigneeServiceDto> getAssigneeByCompany(Company company);

    Assignee findAssigneeById(Long assigneeId);

    List<AssigneeGetDto> getAllAssigneeByCompany(String name);

    AssigneeDetailDto findAssigneeByIdDetailDto(Long id);
}
