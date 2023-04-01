package ru.spring.shop.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    ACTIVE("Доступно"), DISABLE("Недоступно");

    private final String title;
}
