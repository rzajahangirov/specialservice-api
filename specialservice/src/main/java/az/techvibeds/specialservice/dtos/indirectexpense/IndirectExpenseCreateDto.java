package az.techvibeds.specialservice.dtos.indirectexpense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndirectExpenseCreateDto {
    private String name;
    private BigDecimal amount;
    private String currency;
    private Date expenseDate;
    private String category;
}
