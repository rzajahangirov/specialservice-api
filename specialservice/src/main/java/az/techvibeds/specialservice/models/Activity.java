package az.techvibeds.specialservice.models;

import az.techvibeds.specialservice.enums.ActivityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String process;
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @ManyToOne
    private Assignee assignee;

}
