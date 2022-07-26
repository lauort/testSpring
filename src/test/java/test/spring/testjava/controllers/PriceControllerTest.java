package test.spring.testjava.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import test.spring.testjava.dto.PriceOutput;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void shouldReturnCorrectPrice(LocalDateTime localDateTime, Long brandId, Integer productId, BigDecimal value) throws Exception {
        var mvcResult = this.mockMvc.perform(get("/api/prices")
                        .param("brandId", String.valueOf(brandId))
                        .param("productId", String.valueOf(productId))
                        .param("priceDate", String.valueOf(localDateTime)))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        var output = new ObjectMapper().findAndRegisterModules()
                .readValue(mvcResult.getResponse().getContentAsString(), PriceOutput.class);
        assertEquals(value, output.getPrice());
    }

}