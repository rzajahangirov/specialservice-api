package az.techvibeds.specialservice.dtos.service;

import az.techvibeds.specialservice.dtos.assignee.AssigneeServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceGetDataDto {
    private Map<String, Integer> serviceCounts;
    private List<ServiceDescriptionDto> serviceDescriptionDtoList;
    private List<ServiceExecutionStatusDto> serviceExecutionStatusDtoList;
    private List<AssigneeServiceDto> assigneeServiceDtoList;
}
