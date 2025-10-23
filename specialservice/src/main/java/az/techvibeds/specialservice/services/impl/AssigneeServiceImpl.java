package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.assignee.AssigneeServiceDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.repositories.AssigneeRepository;
import az.techvibeds.specialservice.services.AssigneeService;
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

    @Override
    public List<AssigneeServiceDto> getAssigneeByCompany(Company company) {
        return assigneeRepository.findAllByCompany(company)
                .stream()
                .map(assignee -> modelMapper.map(assignee, AssigneeServiceDto.class))
                .collect(Collectors.toList());
    }

}
