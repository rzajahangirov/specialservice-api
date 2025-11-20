package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseCreateDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseReadDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseUpdateDto;
import az.techvibeds.specialservice.models.ConstructionProject;

import java.util.List;

public interface ProjectExpenseService {
    List<ProjectExpenseDto> getExpensesFromProject(List<ConstructionProject> constructionProjectList);

    ProjectExpenseReadDto create(ProjectExpenseCreateDto dto);

    ProjectExpenseReadDto getById(Long id);

    List<ProjectExpenseReadDto> getAll();

    ProjectExpenseReadDto update(Long id, ProjectExpenseUpdateDto dto);

    void delete(Long id);
}
