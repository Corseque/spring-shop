package ru.spring.shop.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.api.product.dto.ProductDto;
import ru.spring.shop.service.*;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;


    @GetMapping("/all")
    public String getProductList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "product/product-list";
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String showForm(Model model, @RequestParam(name = "id", required = false) Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
        } else {
            productDto = new ProductDto();
        }
        model.addAttribute("categoryService", categoryService);
        model.addAttribute("manufacturers", manufacturerService.findAll());
        model.addAttribute("product", productDto);

        model.addAttribute("manufacturerList", manufacturerService.findAll());
        model.addAttribute("categoryList", categoryService.findAll());
        return "product/product-form";
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('product.read')")
    public String showInfo(Model model, @PathVariable(name = "productId") Long id) {
        ProductDto productDto;
        if (id != null) {
            productDto = productService.findById(id);
        } else {
            return "redirect:/product/all";
        }
        model.addAttribute("product", productDto);
        return "product/product-info";
//        return "product/product-form";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('product.create', 'product.update')")
    public String saveProduct(ProductDto product, @RequestParam("file") MultipartFile file) {
        product.setDate(LocalDate.now()); // todo
        productService.save(product, file);
        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('product.delete')")
    public String deleteById(@PathVariable(name = "id") Long id) {
        productService.deleteById(id);
        return "redirect:/product/all";
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(productImageService.loadFileAsImage(id), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }

    //  todo дз 11      Сделать загрузку множества изображений

//    ReviewDto {
//        String productId;
//        String comment;
//        String captchaCode;
//    }
//
    // в маппере наййти productById из БД


    // сделать вывод сообщения об ошибке и валидацию капчи через BindingResult
//    @PostMapping("/review")
//    public String addReview(ReviewDto reviewDto, HttpSession httpSession, Principal principal) {
//        userService.findByUsername(principal.getName());
//
//        reviewService.save(ReviewDto)
//    }
}
