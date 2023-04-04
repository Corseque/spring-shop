package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.api.category.dto.CategoryDto;

import ru.spring.shop.dao.CategoryDao;
import ru.spring.shop.entity.Category;

import ru.spring.shop.entity.enums.Status;
import ru.spring.shop.web.mapper.CategoryMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryDao categoryDao;

    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(category -> categoryMapper.toCategoryDto(category)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto findByTitle(String title) {
        return categoryMapper.toCategoryDto(categoryDao.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("There is no category with name " + title)));
    }

    public CategoryDto findById(Long id) {
        return categoryMapper.toCategoryDto(categoryDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no category with id " + id)));
    }

    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        if (category.getId() != null) {
            categoryDao.findById(category.getId()).ifPresent((c) ->
                    category.setVersion(c.getVersion()));
        }
        return categoryMapper.toCategoryDto(categoryDao.save(category));
    }

    public void deleteById(Long id) {
        categoryDao.findById(id).ifPresent(category -> {
            category.setStatus(Status.DISABLE);
            categoryDao.save(category);
        });
    }
}
