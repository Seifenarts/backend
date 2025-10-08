package de.seifenarts.controller;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.dto.product_dto.respons_dto.ProductResponseDTO;
import de.seifenarts.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/all")
    public Page<ProductResponseDTO> getAllActiveProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAllActiveProducts(pageable);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById (@PathVariable("id") Long productId) {
        return productService.getProductById(productId);
    }

    @PatchMapping("/{id}")
    public ProductResponseDTO toggleProductStatus(@PathVariable("id") Long productId) {
        return productService.setProductActiveStatus(productId);
    }

    @PutMapping("{id}")
    public ProductResponseDTO updateProduct(@PathVariable("id") Long productId, @RequestBody ProductRequestDto dto) {
        return productService.updateProduct(productId, dto);
    }

}
