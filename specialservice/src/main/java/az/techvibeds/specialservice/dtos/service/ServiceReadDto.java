package az.techvibeds.specialservice.dtos.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceReadDto {
    private String name;
    private String description;
    private String amount;
    private LocalDate deadline;
    private String unit;
    private String status;
    private String assignee;
    private String companyName;
}
