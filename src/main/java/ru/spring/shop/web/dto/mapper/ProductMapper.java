package ru.spring.shop.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.spring.shop.dao.CategoryDao;
import ru.spring.shop.dao.ManufacturerDao;
import ru.spring.shop.entity.Category;
import ru.spring.shop.entity.Manufacturer;
import ru.spring.shop.entity.Product;
import ru.spring.shop.web.dto.CategoryDto;
import ru.spring.shop.web.dto.ProductDto;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = ManufacturerMapper.class)
public interface ProductMapper {
    Product toProduct (ProductDto productDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    ProductDto toProductDto (Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(
                () -> new NoSuchElementException("There is no manufacturer with name " + manufacturer));
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }

    default Set<Category> getCategories(Set<CategoryDto> categoriesDto, @Context CategoryDao categoryDao) {
        return categoriesDto.stream().map(categoryDto -> categoryDao.findById(categoryDto.getId())
                        .orElseThrow(() -> new NoSuchElementException("There is no category with id " + categoryDto.getId())))
                .collect(Collectors.toSet());
    }

    default Set<CategoryDto> getCategories(Set<Category> categories) {
        return categories.stream().map(category -> CategoryDto.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .status(category.getStatus())
                        .build())
                .collect(Collectors.toSet());
    }
}
