package az.techvibeds.specialservice.dtos.assignee;

import az.techvibeds.specialservice.dtos.activities.ActivitiesReadDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssigneeDetailDto {
    private String name;
    private Integer activeServiceCount;
    private Integer totalCapacity;
    private List<ActivitiesReadDto> activitiesDto;
}
