package test.spring.testjava.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import test.spring.testjava.models.Brand;

import java.util.Optional;

public interface BrandRepo extends PagingAndSortingRepository<Brand, Long> {

    Optional<Brand> findBrandByName(String zara);
}
