package ru.spring.shop.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.api.product.dto.ProductDto;

import ru.jms.service.JmsSenderService;
import ru.spring.shop.service.ProductService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductRestController {

    private final ProductService productService;

    private final JmsSenderService jmsSenderService;

    @GetMapping("/all")
    public List<ProductDto> getProductsList() {
        return productService.findAll();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "productId") Long id) {
        ProductDto product;
        if (id != null) {
            product = productService.findById(id);
            ProductDto productDto = ProductDto.builder()
                    .id(product.getId())
                    .title(product.getTitle())
                    .cost(product.getCost())
                    .date(product.getDate())
                    .status(product.getStatus())
                    .manufacturer(product.getManufacturer())
                    .categories(product.getCategories()).build();
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Validated @RequestBody ProductDto productDto) {
        ProductDto savedProductDto = productService.save(productDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "productId") Long id,
                                           @Validated @RequestBody ProductDto productDto) {
        productDto.setId(id);
        ProductDto oldProductDto = productService.findById(id);
        ProductDto savedProductDto = productService.save(productDto);
        if (productDto.getCost() != oldProductDto.getCost()) {
            jmsSenderService.sendProductMessage(savedProductDto);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/product/" + savedProductDto.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(name = "productId") Long id) {
        productService.deleteById(id);
    }

}