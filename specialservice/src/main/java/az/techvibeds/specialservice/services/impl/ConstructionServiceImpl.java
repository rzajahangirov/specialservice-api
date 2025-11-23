package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.construction.ConstructionDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectCreateDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectReadDto;
import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectUpdateDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseReadDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageReadDto;
import az.techvibeds.specialservice.models.Company;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.repositories.ConstructionProjectRepository;
import az.techvibeds.specialservice.repositories.ProjectContractorRepository;
import az.techvibeds.specialservice.repositories.ProjectStatusRepository;
import az.techvibeds.specialservice.services.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConstructionServiceImpl implements ConstructionService {
    private final ConstructionProjectService constructionProjectService;
    private final CompanyService companyService;
    private final ProjectStageService projectStageService;
    private final ProjectExpenseService projectExpenseService;
    private final ProjectContractorService projectContractorService;
    private final ModelMapper modelMapper;
    private final ConstructionProjectRepository projectRepository;
    private final ProjectStatusRepository projectStatusRepository;
    private final ProjectContractorRepository projectContractorRepository;

    @Override
    public ConstructionDto getConstructionPageData(String email) {
        ConstructionDto constructionDto = new ConstructionDto();
        Company company = companyService.findByUserEmail(email);
        List<ConstructionProject> constructionProjectList = constructionProjectService.getAllByCompanyId(company.getId());
        constructionDto.setCountOfProjects(constructionProjectList.size());
        Integer countOfActiveProjects = 0;
        Integer countOfCompletedProjects = 0;
        for (ConstructionProject constructionProject : constructionProjectList) {
            LocalDate endDate = constructionProject.getEndDate().plusMonths(6);
            if (constructionProject.getStatus().getName().toUpperCase().equals("ACTIVE")) {
                countOfActiveProjects++;
            }else if (constructionProject.getStatus().getName().toUpperCase().equals("COMPLETED") && LocalDate.now().isBefore(endDate)  ) {
                countOfCompletedProjects++;
            }
        }
        constructionDto.setCountOfActiveProjects(countOfActiveProjects);
        constructionDto.setCountOfCompletedProjects(countOfCompletedProjects);
        BigDecimal budget = company.getBudget();
        BigDecimal usedBudget = company.getUsedBudget();

        BigDecimal percentage = usedBudget
                .divide(budget, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
        constructionDto.setBudgetUsagePercentage(percentage.doubleValue());
        constructionDto.setConstructionProjects(constructionProjectService.mapToConstructionProjectDto(constructionProjectList));
        constructionDto.setProjectStages(projectStageService.getActiveStagesFromProjects(constructionProjectList));
        constructionDto.setProjectExpenses(projectExpenseService.getExpensesFromProject(constructionProjectList));
        constructionDto.setProjectContractors(projectContractorService.getAllByCompanyId(company.getId()));
        return constructionDto;
    }

    @Override
    public ConstructionProjectReadDto createConstructionProject(String userEmail, ConstructionProjectCreateDto dto) {
        ConstructionProject constructionProject = modelMapper.map(dto, ConstructionProject.class);
        constructionProject.setCompany(companyService.findByUserEmail(userEmail));
        constructionProject.setStatus(projectStatusRepository.findById(dto.getStatusId()).orElseThrow(() -> new RuntimeException("Status not found")));
        constructionProject.setProjectContractor(projectContractorRepository.findById(dto.getProjectContractorId()).orElseThrow(() -> new RuntimeException("Contractor not found")));
        constructionProject.setExpenses(new ArrayList<>());
        constructionProject.setStages(new ArrayList<>());//heleki bura tam bilinmir
        projectRepository.save(constructionProject);
        return mapToReadDto(constructionProject);
    }
    @Override
    public ConstructionProjectReadDto updateConstructionProject(Long id, ConstructionProjectUpdateDto dto, String userEmail) {

        ConstructionProject entity = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project can not find: " + id));
        if (entity.getCompany() != companyService.findByUserEmail(userEmail)) {
            throw new EntityNotFoundException("Access denied");
        }
        entity.setName(dto.getName());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setBudget(dto.getBudget());
        entity.setProjectManager(dto.getProjectManager());

        ConstructionProject savedEntity = projectRepository.save(entity);

        return mapToReadDto(savedEntity);
    }


    @Override
    public void deleteConstructionProject(Long id, String userEmail) {
        ConstructionProject project = projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project can not find: " + id));
        if (project.getCompany() != companyService.findByUserEmail(userEmail)) {
            throw new EntityNotFoundException("Access denied");
        }
        projectRepository.delete(project);
    }

    private ConstructionProjectReadDto mapToReadDto(ConstructionProject constructionProject) {
        ConstructionProjectReadDto dto = modelMapper.map(constructionProject, ConstructionProjectReadDto.class);
        dto.setStatusName(constructionProject.getStatus().getName());
        dto.setProjectContractorName(constructionProject.getProjectContractor().getName());
        dto.setExpensesList(constructionProject.getExpenses()
                .stream()
                .map(projectExpense -> modelMapper
                        .map(projectExpense, ProjectExpenseReadDto.class))
                .collect(Collectors.toList()));
        dto.setStagesList(constructionProject.getStages()
        .stream()
                .map(projectStage -> {
                    ProjectStageReadDto projectStageReadDto = modelMapper.map(projectStage, ProjectStageReadDto.class);
                    projectStageReadDto.setStagesStatus(projectStage.getStatus().name());
                    return projectStageReadDto;
                })
                .collect(Collectors.toList()));
        return dto;

    }

}
