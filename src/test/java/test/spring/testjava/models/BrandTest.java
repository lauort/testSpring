package test.spring.testjava.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = { "spring.liquibase.contexts=models" })
class BrandTest {

    public static final String ZARA = "ZARA";
    @Autowired
    private TestEntityManager entityManager;

    @ParameterizedTest
    @ValueSource(strings = {"", "          "})
    void shouldNotAllowEmptyOrBlankName(String name) {
        var exception = assertThrows(ConstraintViolationException.class, () ->
                this.entityManager.persistAndFlush(Brand.builder().name(name).build()));

        assertTrue(exception.getConstraintViolations().stream().findFirst().isPresent());
        assertEquals("Blank or empty name not allowed",
                exception.getConstraintViolations().stream().findFirst().get().getMessage());
    }

    @Test
    void shouldStoreBrand() {
        var brand = this.entityManager.persistAndFlush(Brand.builder().name(ZARA).build());
        var brandInDatabase = this.entityManager.find(Brand.class, brand.getId());
        assertNotNull(brandInDatabase.getId());
        assertEquals(ZARA, brandInDatabase.getName());
    }

    @Test
    void shouldNotAllowDuplicateBrands() {
        this.entityManager.persistAndFlush(Brand.builder().name(ZARA).build());
        assertThrows(PersistenceException.class, () ->
                this.entityManager.persistAndFlush(Brand.builder().name(ZARA).build()));
    }

}