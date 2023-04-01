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
public class CategoryDto {

    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private Status status;

//    private Set<String> products;

}
