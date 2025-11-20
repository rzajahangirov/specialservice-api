package az.techvibeds.specialservice.dtos.projectexpense;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectExpenseReadDto {
    private Long id;
    private String name;
    private BigDecimal amount;
    private LocalDate date;
}

