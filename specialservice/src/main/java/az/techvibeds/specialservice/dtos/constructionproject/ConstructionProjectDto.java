package az.techvibeds.specialservice.dtos.constructionproject;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConstructionProjectDto {
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String  status;
    private BigDecimal budget;
    private String projectManager;
}
