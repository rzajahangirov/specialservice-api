package az.techvibeds.specialservice.dtos.projectcontractor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectContractorReadDto {
    private Long id;
    private String name;
    private String contactPerson;
    private String phoneNumber;
    private String email;
}

