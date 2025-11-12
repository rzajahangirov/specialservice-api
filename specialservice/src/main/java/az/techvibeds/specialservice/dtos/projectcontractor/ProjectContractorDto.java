package az.techvibeds.specialservice.dtos.projectcontractor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectContractorDto {
    private String name;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private Integer projectCount;
}
