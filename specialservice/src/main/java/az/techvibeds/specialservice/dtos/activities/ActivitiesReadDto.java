package az.techvibeds.specialservice.dtos.activities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivitiesReadDto {
    private Long id;
    private String process;
    private LocalDateTime startTime;
    private String activityStatusDto;
}
