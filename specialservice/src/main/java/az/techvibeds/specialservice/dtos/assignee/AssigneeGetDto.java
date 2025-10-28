package az.techvibeds.specialservice.dtos.assignee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssigneeGetDto {
    private Long id;
    private String name;
}
