package test.spring.testjava.repositories;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import test.spring.testjava.models.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
class PriceRepoTest {

    @Autowired
    private PriceRepo priceRepo;

    public static Stream<Arguments> searchParameters() {
        return Stream.of(
                arguments(LocalDateTime.of(2020, Month.JUNE, 14, 10, 0),
                        1L, 35455, new BigDecimal("35.50")),
                arguments(LocalDateTime.of(2020, Month.JUNE, 14, 16, 0),
                        1L, 35455, new BigDecimal("25.45")),
                arguments(LocalDateTime.of(2020, Month.JUNE, 14, 21, 0),
                        1L, 35455, new BigDecimal("35.50")),
                arguments(LocalDateTime.of(2020, Month.JUNE, 15, 10, 0),
                        1L, 35455, new BigDecimal("30.50")),
                arguments(LocalDateTime.of(2020, Month.JUNE, 16, 21, 0),
                        1L, 35455, new BigDecimal("38.95"))
        );
    }


    @ParameterizedTest
    @MethodSource("searchParameters")
    void shouldCorrectPrice(LocalDateTime localDateTime, Long brandId, Integer productId, BigDecimal price) {
        List<Price> priceList = priceRepo.findByDateAndBrandOrderByPriority(localDateTime, brandId, productId);
        assertFalse(priceList.isEmpty());
        assertEquals(price, priceList.get(0).getValue());

    }










}