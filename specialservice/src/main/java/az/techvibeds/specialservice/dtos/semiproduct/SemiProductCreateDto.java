package az.techvibeds.specialservice.dtos.semiproduct;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SemiProductCreateDto {
    private String name;
    private String description;
    private String  unitType;
    private Integer stockQuantity;
    private String productStatus;
}
