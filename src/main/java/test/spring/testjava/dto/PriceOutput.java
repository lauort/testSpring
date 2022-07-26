package test.spring.testjava.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PriceOutput {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private BigDecimal price;

    private Currency curr;
}
