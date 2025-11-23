package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseReadDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseUpdateDto;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.models.ProjectExpense;
import az.techvibeds.specialservice.repositories.ProjectExpenseRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import az.techvibeds.specialservice.services.ProjectExpenseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectExpenseServiceImpl implements ProjectExpenseService {
    private final ProjectExpenseRepository projectExpenseRepository;
    private final ModelMapper modelMapper;
    private final ConstructionProjectService constructionProjectService;
    private final CompanyService companyService;

    @Override
    public List<ProjectExpenseDto> getExpensesFromProject(List<ConstructionProject> constructionProjectList) {
        List<ProjectExpenseDto> projectExpenseDtoList = new ArrayList<>();
        for (ConstructionProject constructionProject : constructionProjectList) {
            List<ProjectExpense> projectExpenseList = constructionProject.getExpenses();
            for (ProjectExpense projectExpense : projectExpenseList) {
                ProjectExpenseDto projectExpenseDto = modelMapper.map(projectExpense, ProjectExpenseDto.class);
                projectExpenseDto.setProjectName(constructionProject.getName());
                projectExpenseDtoList.add(projectExpenseDto);
            }
        }
        return projectExpenseDtoList;
    }
    @Override
    public ProjectExpenseReadDto create(ProjectExpenseCreateDto dto) {

        ProjectExpense expense = new ProjectExpense();
        expense.setName(dto.getName());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());

        ConstructionProject project = constructionProjectService.findById(dto.getProjectId());
        expense.setProject(project);

        projectExpenseRepository.save(expense);

        return mapToRead(expense);
    }

    @Override
    public ProjectExpenseReadDto getById(Long id, String userEmail) {
        ProjectExpense expense = projectExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        if (expense.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
            return mapToRead(expense);
        }else {
            throw new RuntimeException("Access denied");
        }


    }

    @Override
    public List<ProjectExpenseReadDto> getAll(String userEmail) {
        List<ProjectExpense> projectExpenseList = projectExpenseRepository.findAll();
        List<ProjectExpenseReadDto> projectExpenseReadDtoList = new ArrayList<>();
        for (ProjectExpense exp : projectExpenseList) {
            if (exp.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
                ProjectExpenseReadDto dto = modelMapper.map(exp, ProjectExpenseReadDto.class);
                dto.setProjectName(exp.getProject().getName());
                projectExpenseReadDtoList.add(dto);
            }
        }
        return projectExpenseReadDtoList;
    }

    @Override
    public ProjectExpenseReadDto update(Long id, ProjectExpenseUpdateDto dto, String userEmail) {
        ProjectExpense expense = projectExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (expense.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
            expense.setName(dto.getName());
            expense.setAmount(dto.getAmount());
            expense.setDate(dto.getDate());

            projectExpenseRepository.save(expense);
            return mapToRead(expense);
        }else {
            throw new RuntimeException("Access denied");
        }
    }

    @Override
    public void delete(Long id, String userEmail) {
        ProjectExpense projectExpense = projectExpenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
       if (projectExpense.getProject().getCompany() == companyService.findByUserEmail(userEmail)) {
           projectExpenseRepository.delete(projectExpense);
       }else {
           throw new RuntimeException("Access denied");
       }
    }
    private ProjectExpenseReadDto mapToRead(ProjectExpense expense) {
        ProjectExpenseReadDto expenseDto = modelMapper.map(expense, ProjectExpenseReadDto.class);
        expenseDto.setProjectName(expense.getProject().getName());
        return expenseDto;
    }
}
