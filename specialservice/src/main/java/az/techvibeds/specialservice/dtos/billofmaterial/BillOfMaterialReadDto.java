package az.techvibeds.specialservice.dtos.billofmaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillOfMaterialReadDto {
    private Long id;
    private String productName;
    private String name;
    private String bomStatus;
    private LocalDate creationDate;
}
