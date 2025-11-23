package az.techvibeds.specialservice.dtos.projectstage;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectStageUpdateDto {
    private String name;
    private Integer stageNumber;
    private Double progressPercentage;
    private String statusName;
}
