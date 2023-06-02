package ru.spring.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;
import ru.api.product.dto.ProductDto;
import ru.spring.shop.dao.CategoryDao;
import ru.spring.shop.dao.ProductDao;
import ru.spring.shop.entity.Product;

import ru.spring.shop.entity.ProductImage;
import ru.spring.shop.entity.enums.Status;
import ru.spring.shop.web.mapper.ProductMapper;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;
    private final CategoryDao categoryDao;
    private final ProductImageService productImageService;

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return productDao.findAll().stream()
                .map(product -> productMapper.toProductDto(product))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        return productMapper.toProductDto(productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no product with id " + id)));
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProductById(Long id) {
        return productDao.findById(id);
    }

    @Transactional(readOnly = true)
    public ProductDto findByTitle(String title) {
        return productMapper.toProductDto(productDao.findByTitle(title)
                .orElseThrow(() -> new NoSuchElementException("There is no product with title " + title)));
    }
//
//    public ProductDto save(ProductDto productDto, MultipartFile multipartFile) {
//        Product product = productMapper.toProduct(productDto, categoryDao);
//        if (product.getId() != null) {
//            productDao.findById(product.getId()).ifPresent((p) ->
//                    product.setVersion(p.getVersion())
//            );
//        }
//        if (multipartFile != null && !multipartFile.isEmpty()) {
//            String pathToSavedFile = productImageService.save(multipartFile);
//            ProductImage productImage = ProductImage.builder()
//                    .path(pathToSavedFile)
//                    .product(product)
//                    .build();
//            product.addImage(productImage);
//        }
//        return productMapper.toProductDto(productDao.save(product));
//    }

    public ProductDto save(ProductDto productDto, MultipartFile[] multipartFiles) {
        Product product = productMapper.toProduct(productDto, categoryDao);
        if (product.getId() != null) {
            productDao.findById(product.getId()).ifPresent((p) ->
                    product.setVersion(p.getVersion())
            );
        }
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String pathToSavedFile = productImageService.save(multipartFile);
                ProductImage productImage = ProductImage.builder()
                        .path(pathToSavedFile)
                        .product(product)
                        .build();
                product.addImage(productImage);
            }
        }
        return productMapper.toProductDto(productDao.save(product));
    }

    @Transactional
    public ProductDto save(final ProductDto productDto) {
        return save(productDto, null);
    }

    public void deleteById(Long id) {
        Optional<Product> product = productDao.findById(id);
        product.ifPresent(p -> {
            p.setStatus(Status.DISABLE);
            productDao.save(p);
        });
    }

    public void delete(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto, categoryDao);
        if (product.getId() != null) {
            product.setStatus(Status.DISABLE);
            productDao.save(product);
        }
    }

    public List<ProductImage> getImagesByProductId(Long id) {
        return productDao.findById(id)
                .orElseThrow(() -> new NoSuchElementException("There is no product with id " + id)).getImages();
    }
}
