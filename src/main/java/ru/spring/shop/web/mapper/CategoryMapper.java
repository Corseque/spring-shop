package ru.spring.shop.web.mapper;

import org.mapstruct.Mapper;
import ru.api.category.dto.CategoryDto;
import ru.spring.shop.entity.Category;
import ru.spring.shop.entity.enums.Status;

@Mapper
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);

    default Status getStatus(String status) {
        return Status.valueOf(status);
    }

    default String getStatus(Status status) {
        return status.getTitle();
    }
}
