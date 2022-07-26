package test.spring.testjava.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "PRICES")
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="BRAND_ID", nullable=false)
    private Brand brand;

    @Column()
    @NotNull(message = "Null not allowed")
    private LocalDateTime startDate;

    @Column()
    @NotNull(message = "Null not allowed")
    private LocalDateTime endDate;

    @Column()
    @NotNull(message = "Null not allowed")
    private Integer priceList;

    @Column()
    @NotNull(message = "Null not allowed")
    private Integer productId;

    @Column()
    @NotNull(message = "Null not allowed")
    private Integer priority;

    @Column(name = "PRICE")
    @NotNull(message = "Null not allowed")
    private BigDecimal value;

    @Column()
    @NotNull(message = "Null not allowed")
    private Currency curr;

    @Builder
    public Price(LocalDateTime startDate, LocalDateTime endDate,
                 Brand brand, Integer productId, Integer priority,
                 BigDecimal value, Currency curr) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.productId = productId;
        this.priority = priority;
        this.value = value;
        this.curr = curr;
        this.brand = brand;
    }
}


