package az.techvibeds.specialservice.dtos.constructionproject;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionProjectCreateDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long statusId;
    private BigDecimal budget;
    private String projectManager;
    private Long projectContractorId;
    //expense ve stage null(stages unknown)
}
