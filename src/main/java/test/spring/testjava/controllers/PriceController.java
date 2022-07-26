package test.spring.testjava.controllers;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.spring.testjava.dto.PriceOutput;
import test.spring.testjava.models.Price;
import test.spring.testjava.repositories.PriceRepo;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController()
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceRepo priceRepo;

    public PriceController(PriceRepo priceRepo) {
        this.priceRepo = priceRepo;
    }

    @GetMapping("")
    public PriceOutput readTopPriceByBrandDateAndProduct(@RequestParam(value = "brandId") Long brandId,
                                                    @RequestParam(value = "productId") Integer productId,
                                                    @RequestParam(value = "priceDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime priceDate) {
        Optional<Price> optionalPrice = priceRepo.findByDateAndBrandOrderByPriority(priceDate, brandId, productId)
                .stream().findFirst();

        return optionalPrice.isEmpty() ?
                PriceOutput.builder().build() :
                PriceOutput.builder()
                        .startDate(optionalPrice.get().getStartDate())
                        .endDate(optionalPrice.get().getStartDate())
                        .price(optionalPrice.get().getValue())
                        .curr(optionalPrice.get().getCurr())
                        .build();
    }


}
