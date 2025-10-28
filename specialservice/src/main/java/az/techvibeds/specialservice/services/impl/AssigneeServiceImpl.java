package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.assignee.AssigneeDetailDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeGetDto;
import az.techvibeds.specialservice.dtos.assignee.AssigneeServiceDto;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.repositories.AssigneeRepository;
import az.techvibeds.specialservice.services.AssigneeService;
import az.techvibeds.specialservice.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssigneeServiceImpl implements AssigneeService {
    private final ModelMapper modelMapper;
    private final AssigneeRepository assigneeRepository;
    private final CompanyService companyService;

    @Override
    public List<AssigneeServiceDto> getAssigneeByCompany(Company company) {
        return assigneeRepository.findAllByCompany(company)
                .stream()
                .map(assignee -> modelMapper.map(assignee, AssigneeServiceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Assignee findAssigneeById(Long assigneeId) {
        return assigneeRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee cannot be found"));

    }

    @Override
    public List<AssigneeGetDto> getAllAssigneeByCompany(String name) {
        Company company = companyService.findByUserEmail(name);
        return assigneeRepository.findAllByCompany(company)
                .stream()
                .map(assignee -> modelMapper.map(assignee, AssigneeGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AssigneeDetailDto findAssigneeByIdDetailDto(Long id) {
        Assignee assignee = findAssigneeById(id);
        AssigneeDetailDto assigneeDetailDto = modelMapper.map(assignee, AssigneeDetailDto.class);
        return assigneeDetailDto;
    }

}
