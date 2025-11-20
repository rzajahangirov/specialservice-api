package az.techvibeds.specialservice.services.impl;

import az.techvibeds.specialservice.dtos.projectstage.ProjectStageDto;
import az.techvibeds.specialservice.models.ConstructionProject;
import az.techvibeds.specialservice.models.ProjectStage;
import az.techvibeds.specialservice.repositories.ProjectStageRepository;
import az.techvibeds.specialservice.services.ProjectStageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectStageServiceImpl implements ProjectStageService {
    private final ProjectStageRepository projectStageRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ProjectStageDto> getActiveStagesFromProjects(List<ConstructionProject> constructionProjectList) {
        List<ProjectStageDto> activeStages = new ArrayList<>();
        for (ConstructionProject constructionProject : constructionProjectList) {
            List<ProjectStage> projectStageList = constructionProject.getStages();
            for (ProjectStage projectStage : projectStageList) {
                if (projectStage.getStatus().name().equals("ACTIVE")) {
                    ProjectStageDto projectStageDto = modelMapper.map(projectStage, ProjectStageDto.class);
                    projectStageDto.setProjectName(constructionProject.getName());
                    activeStages.add(projectStageDto);
                }
            }
        }
        return activeStages;
    }
}
