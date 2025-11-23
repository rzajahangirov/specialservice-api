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

    ProjectExpenseReadDto getById(Long id, String userEmail);

    List<ProjectExpenseReadDto> getAll(String userEmail);

    ProjectExpenseReadDto update(Long id, ProjectExpenseUpdateDto dto, String userEmail);

    void delete(Long id, String userEmail);
}
