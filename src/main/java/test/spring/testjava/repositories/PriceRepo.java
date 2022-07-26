package test.spring.testjava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import test.spring.testjava.models.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepo extends PagingAndSortingRepository<Price, Long> {

    @Query("SELECT price FROM Price price " +
            "WHERE ?1 BETWEEN price.startDate AND price.endDate " +
            "AND price.brand.id = ?2 " +
            "AND price.productId = ?3 ORDER BY price.priority DESC ")
    List<Price> findByDateAndBrandOrderByPriority(LocalDateTime localDateTime, Long brandId, Integer productId);

}
