package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseReadDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseUpdateDto;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.models.ProjectExpense;
import az.techvibeds.specialservice.repositories.ProjectExpenseRepository;
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
        ProjectExpense expense = modelMapper.map(dto, ProjectExpense.class);

        expense.setProject(null);

        projectExpenseRepository.save(expense);
        return modelMapper.map(expense, ProjectExpenseReadDto.class);
    }

    @Override
    public ProjectExpenseReadDto getById(Long id) {
        ProjectExpense expense = projectExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));


        return modelMapper.map(expense, ProjectExpenseReadDto.class);
    }

    @Override
    public List<ProjectExpenseReadDto> getAll() {
        return projectExpenseRepository.findAll()
                .stream()
                .map(exp -> modelMapper.map(exp, ProjectExpenseReadDto.class))
                .toList();
    }

    @Override
    public ProjectExpenseReadDto update(Long id, ProjectExpenseUpdateDto dto) {
        ProjectExpense expense = projectExpenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));


        expense.setName(dto.getName());
        expense.setAmount(dto.getAmount());
        expense.setDate(dto.getDate());

        projectExpenseRepository.save(expense);
        return modelMapper.map(expense, ProjectExpenseReadDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!projectExpenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found");
        }
        projectExpenseRepository.deleteById(id);
    }
}
