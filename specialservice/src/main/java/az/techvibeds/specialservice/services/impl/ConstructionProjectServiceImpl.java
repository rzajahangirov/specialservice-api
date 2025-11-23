package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectDto;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.repositories.ConstructionProjectRepository;
import az.techvibeds.specialservice.services.CompanyService;
import az.techvibeds.specialservice.services.ConstructionProjectService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConstructionProjectServiceImpl implements ConstructionProjectService {
    private final ConstructionProjectRepository constructionProjectRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    @Override
    public List<ConstructionProject> getAllByCompanyId(Long companyId) {
        return constructionProjectRepository.findAllByCompany_Id(companyId);
    }

    @Override
    public List<ConstructionProjectDto> mapToConstructionProjectDto(List<ConstructionProject> constructionProjectList) {
        return constructionProjectList
                .stream()
                .map(project -> {
                    ConstructionProjectDto dto = new ConstructionProjectDto();
                    dto.setId(project.getId());
                    dto.setName(project.getName());
                    dto.setStartDate(project.getStartDate());
                    dto.setEndDate(project.getEndDate());
                    dto.setProjectStatus(project.getStatus() != null ? project.getStatus().getName() : null);
                    dto.setBudget(project.getBudget());
                    dto.setProjectManager(project.getProjectManager());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ConstructionProject findById(Long id) {
        return constructionProjectRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public List<ConstructionProjectDto> getFilteredProjects(Long statusId, String projectName, String userEmail) {
        Long companyId = companyService.findByUserEmail(userEmail).getId();


        List<ConstructionProject> projectList = constructionProjectRepository
                .findAllByCompany_Id(companyId);

        List<ConstructionProject> filteredProjects = new ArrayList<>();


        if (statusId != null || (projectName != null && !projectName.isEmpty())) {

            for (ConstructionProject project : projectList) {

                boolean statusMatches = (statusId == null)
                        || project.getStatus().getId().equals(Long.valueOf(statusId));

                boolean nameMatches = (projectName == null || projectName.isEmpty())
                        || project.getName().toLowerCase().contains(projectName.toLowerCase());

                if (statusMatches && nameMatches) {
                    filteredProjects.add(project);
                }
            }

            return projectToProjectDto(filteredProjects);

        } else {
            return projectToProjectDto(projectList);
        }
    }
    private List<ConstructionProjectDto> projectToProjectDto(List<ConstructionProject> projectList) {
        return projectList.stream()
                .map(project -> modelMapper.map(project, ConstructionProjectDto.class))
                .toList();
    }

}
