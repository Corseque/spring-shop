package ru.spring.shop.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ImageStatus {
    ICON_PICTURE ("Логотип"),
    FORM_PICTURE ("Изображение продукта");

    private final String title;
}
