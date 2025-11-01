package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.assignee.*;
import az.techvibeds.specialservice.models.Assignee;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.repositories.AssigneeRepository;
import az.techvibeds.specialservice.services.AssigneeService;
import az.techvibeds.specialservice.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    @Override
    public AssigneeReadDto create(AssigneeCreateDto dto, Principal principal) {
        String email = principal.getName();
        Company company = companyService.findByUserEmail(email);

        Assignee assignee = new Assignee();
        assignee.setName(dto.getName());
        assignee.setTotalCapacity(dto.getTotalCapacity());
        assignee.setActiveServiceCount(0);
        assignee.setCompany(company);
        assigneeRepository.save(assignee);

        return modelMapper.map(assignee, AssigneeReadDto.class);
    }

    @Override
    public AssigneeReadDto update(AssigneeUpdateDto dto) {
        Assignee assignee = assigneeRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Assignee not found"));

        assignee.setName(dto.getName());
        assignee.setTotalCapacity(dto.getTotalCapacity());
        assignee.setActiveServiceCount(dto.getActiveServiceCount());
        assigneeRepository.save(assignee);

        return modelMapper.map(assignee, AssigneeReadDto.class);
    }


    @Override
    public void delete(Long id) {
        assigneeRepository.deleteById(id);
    }
    @Override
    public List<AssigneeReadDto> getAll() {
        return assigneeRepository.findAll()
                .stream()
                .map(assignee -> modelMapper.map(assignee, AssigneeReadDto.class))
                .collect(Collectors.toList());
    }

}
