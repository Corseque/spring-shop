package ru.spring.shop.web.dto;

import lombok.*;
import ru.spring.shop.entity.enums.Status;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 6, fraction = 2)
    private BigDecimal cost;

    @PastOrPresent
    private LocalDate date;

    @NotNull
    private Status status;

    @NotNull
    private String manufacturer;

    @NotNull
    private Set<CategoryDto> categories;
}
