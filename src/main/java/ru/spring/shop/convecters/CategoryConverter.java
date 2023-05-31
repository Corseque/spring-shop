package ru.spring.shop.convecters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.api.category.dto.CategoryDto;
import ru.spring.shop.dao.CategoryDao;
import ru.spring.shop.web.mapper.CategoryMapper;


@Component
@RequiredArgsConstructor
public class CategoryConverter implements Converter<String, CategoryDto> {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto convert(String source) {
        return categoryMapper.toCategoryDto(categoryDao.findById(Long.valueOf(source)).orElse(null));
    }
}
