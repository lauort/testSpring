package test.spring.testjava.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import test.spring.testjava.models.Brand;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BrandRepoTest {

    @Autowired
    private BrandRepo brandRepo;

    @Test
    void shouldReturnAllBrands() {
        var brandArrayList = new ArrayList<Brand>();
        brandRepo.findAll().forEach(brandArrayList::add);
        assertEquals(1, brandArrayList.size());
        assertEquals("ZARA", brandArrayList.get(0).getName());
    }

    @Test
    void shouldReturnBrandByName() {
        Optional<Brand> brandOptional = brandRepo.findBrandByName("ZARA");
        assertTrue(brandOptional.isPresent());
        assertEquals("ZARA", brandOptional.get().getName());
    }

    @Test
    void shouldNotAllowDuplicateBrands() {
        assertThrows(DataIntegrityViolationException.class, () ->
                this.brandRepo.save(Brand.builder().name("ZARA").build()));
    }

}