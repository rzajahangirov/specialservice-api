package az.techvibeds.specialservice.dtos.warehouse;

import lombok.Data;

@Data
public class WarehouseUpdateDto {
    private Long id;
    private String name;
    private String address;
}
