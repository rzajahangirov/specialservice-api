package az.techvibeds.specialservice.dtos.partner;


import az.techvibeds.specialservice.models.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerCreateDto {
    private String name;
    private String ContactPerson;
    private String email;
    private String phone;
    private BigDecimal balance;
    private String currency;
}
