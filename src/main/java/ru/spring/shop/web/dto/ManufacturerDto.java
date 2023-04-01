package ru.spring.shop.web.dto;

import lombok.*;
import ru.spring.shop.entity.enums.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ManufacturerDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Status status;

//    private Set<String> products;

}
