package ru.spring.shop.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.spring.shop.service.CategoryService;
import ru.spring.shop.web.dto.CategoryDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

//todo реализовать функционал
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public List<CategoryDto> getManufacturerList() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getManufacturer(@PathVariable(name = "categoryId") Long id) {
        CategoryDto category = categoryService.findById(id);
        if (category.getId() != null) {
            CategoryDto categoryDto = new CategoryDto().builder()
                    .id(category.getId())
                    .title(category.getTitle())
                    .status(category.getStatus())
                    .build();
            return new ResponseEntity<>(categoryDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addManufacturer(@Validated @RequestBody CategoryDto categoryDto) throws URISyntaxException {
        CategoryDto savedCategory = categoryService.save(categoryDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/api/v1/category/" + savedCategory.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateManufacturer(@PathVariable(name = "categoryId") Long id,
                                                @Validated @RequestBody CategoryDto categoryDto) throws URISyntaxException {
        categoryDto.setId(id);
        CategoryDto savedCategory = categoryService.save(categoryDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/api/v1/category/" + savedCategory.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "categoryId") Long id) {
        categoryService.deleteById(id);
    }


}
