package az.techvibeds.specialservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String productCode;
    private Long stock;
    private BigDecimal price;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Status status;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Company company;
}
