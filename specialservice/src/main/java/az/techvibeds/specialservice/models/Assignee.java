package az.techvibeds.specialservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assignees")
public class Assignee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer activeServiceCount;
    private Integer totalCapacity;

    @ManyToOne
    private Company company;

    @OneToMany(mappedBy = "assignee")
    private List<Activity> activities;
}
