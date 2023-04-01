package ru.spring.shop.web.dto.mapper;

import org.mapstruct.Mapper;
import ru.spring.shop.entity.Category;
import ru.spring.shop.web.dto.CategoryDto;

@Mapper
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);
}
