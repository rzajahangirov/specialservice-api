package az.techvibeds.specialservice.dtos.projectstage;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStageDto {
    private String projectName;
    private String stageName;
    private Integer stageNumber;
    private Double progressPercentage;
}
