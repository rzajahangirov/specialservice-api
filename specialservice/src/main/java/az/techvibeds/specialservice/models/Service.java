package az.techvibeds.specialservice.models;

import az.techvibeds.specialservice.enums.ServiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String amount;
    private LocalDate deadline;

    @ManyToOne
    private Unit unit;

    @Enumerated(EnumType.STRING)
    private ServiceStatus status;

    @ManyToOne
    private Assignee assignee;

    @ManyToOne
    private Company company;


}
