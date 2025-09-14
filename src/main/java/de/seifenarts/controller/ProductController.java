package de.seifenarts.controller;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Long createProduct(
            @RequestPart("product") ProductRequestDto productRequestDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        if (images != null) {
            productRequestDto.setImages(images);
        }

        return productService.addNewProduct(productRequestDto);
    }
}
