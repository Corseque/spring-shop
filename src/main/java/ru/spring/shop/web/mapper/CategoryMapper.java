package ru.spring.shop.web.mapper;

import org.mapstruct.Mapper;
import ru.api.category.dto.CategoryDto;
import ru.spring.shop.entity.Category;

@Mapper
public interface CategoryMapper {

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);


}
