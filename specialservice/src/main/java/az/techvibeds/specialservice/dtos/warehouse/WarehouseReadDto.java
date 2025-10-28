package az.techvibeds.specialservice.dtos.warehouse;

import lombok.Data;

@Data
public class WarehouseReadDto {
    private Long id;
    private String name;
    private String address;
    private String companyName;
}
