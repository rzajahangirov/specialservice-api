package az.techvibeds.specialservice.dtos.billofmaterial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillOfMaterialUpdateDto {
    private Long productId;
    private String name;
    private String bomStatus;
    private LocalDate creationDate;
}
