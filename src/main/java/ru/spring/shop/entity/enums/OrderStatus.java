package ru.spring.shop.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OrderStatus {
    PREPARING("Подготовка"),
    CREATED("Создан"),
    DELIVERING("Передан курьеру"),
    RECEIVED("Получен"),
    CLOSED("Завершен"),
    CANCELED("Отменен"),
    PROCESSING("В обработке");

    private final String title;
}
