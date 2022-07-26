package test.spring.testjava.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.validation.ConstraintViolationException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(properties = { "spring.liquibase.contexts=models" })
class PriceTest {

    @Autowired
    private TestEntityManager entityManager;

    @ParameterizedTest
    @MethodSource("priceGenerator")
    void shouldCheckNotNullValues(Price price, Integer numberOfErrors) {
        var exception = assertThrows(ConstraintViolationException.class, () ->
                this.entityManager.persistAndFlush(price));

        assertEquals(numberOfErrors ,exception.getConstraintViolations().size());
    }

    private static Stream<Arguments> priceGenerator() {
        return Stream.of(
               Arguments.of(Price.builder().build(), 7),
                Arguments.of(Price.builder().productId(1).build(),6),
                Arguments.of(Price.builder().productId(1).priority(1).build(),5)
       );
    }

}