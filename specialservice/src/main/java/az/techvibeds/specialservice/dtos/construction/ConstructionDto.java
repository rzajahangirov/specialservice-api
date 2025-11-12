package az.techvibeds.specialservice.dtos.construction;

import az.techvibeds.specialservice.dtos.constructionproject.ConstructionProjectDto;
import az.techvibeds.specialservice.dtos.projectcontractor.ProjectContractorDto;
import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionDto {
    private Integer countOfProjects;
    private Integer countOfActiveProjects;
    private Integer countOfCompletedProjects;
    private Double budgetUsagePercentage;
    private List<ConstructionProjectDto> constructionProjects;
    private List<ProjectStageDto> projectStages;
    private List<ProjectExpenseDto> projectExpenses;
    private List<ProjectContractorDto> projectContractors;
}
