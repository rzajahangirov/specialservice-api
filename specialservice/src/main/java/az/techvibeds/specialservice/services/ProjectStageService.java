package az.techvibeds.specialservice.services;

import az.techvibeds.specialservice.dtos.projectstage.ProjectStageDto;
import az.techvibeds.specialservice.models.ConstructionProject;

import java.util.List;

public interface ProjectStageService {
    List<ProjectStageDto> getActiveStagesFromProjects(List<ConstructionProject> constructionProjectList);
}
