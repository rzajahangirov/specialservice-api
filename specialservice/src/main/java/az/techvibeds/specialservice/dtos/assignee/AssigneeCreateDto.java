package az.techvibeds.specialservice.dtos.assignee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssigneeCreateDto {
    private String name;
    private Integer totalCapacity;
}
