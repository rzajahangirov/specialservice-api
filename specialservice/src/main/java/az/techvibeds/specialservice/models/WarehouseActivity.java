package az.techvibeds.specialservice.models;

import az.techvibeds.specialservice.enums.ActivityTypeWarehouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warehouse_activities")
public class WarehouseActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityTypeWarehouse type;

    @ManyToOne
    private WarehouseProduct product;

    private Integer quantity;

    @ManyToOne
    private Warehouse sourceWarehouse;
    @ManyToOne
    private Warehouse destinationWarehouse;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

}
