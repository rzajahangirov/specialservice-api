package az.techvibeds.specialservice.dtos.service;

import az.techvibeds.specialservice.dtos.assignee.AssigneeGetDto;
import az.techvibeds.specialservice.dtos.unit.UnitGetDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCreateGetDataDto {
    private List<AssigneeGetDto> assignees;
    private List<UnitGetDto> units;
}
