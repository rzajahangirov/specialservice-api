package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.assignee.*;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.models.Company;

import java.security.Principal;
import java.util.List;

public interface AssigneeService {
    List<AssigneeServiceDto> getAssigneeByCompany(Company company);

    Assignee findAssigneeById(Long assigneeId);

    List<AssigneeGetDto> getAllAssigneeByCompany(String name);

    AssigneeDetailDto findAssigneeByIdDetailDto(Long id);

    AssigneeReadDto create(AssigneeCreateDto dto, Principal principal);

    AssigneeReadDto update(AssigneeUpdateDto dto);

    void delete(Long id);

    List<AssigneeReadDto> getAll();
}
