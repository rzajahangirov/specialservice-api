package az.techvibeds.specialservice.dtos.module;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleReadDto {
    private Long id;
    private String name;
    private String icon;
}
