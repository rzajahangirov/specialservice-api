package az.techvibeds.specialservice.dtos.constructionproject;

import az.techvibeds.specialservice.dtos.projectexpense.ProjectExpenseReadDto;
import az.techvibeds.specialservice.dtos.projectstage.ProjectStageReadDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionProjectReadDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String companyName;
    private String statusName;
    private BigDecimal budget;
    private String projectManager;
    private String projectContractorName;
    private List<ProjectExpenseReadDto> expensesList;
    private List<ProjectStageReadDto> stagesList;

}
