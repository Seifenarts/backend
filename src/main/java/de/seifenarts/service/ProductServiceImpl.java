package de.seifenarts.service;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.entity.Image;
import de.seifenarts.domain.entity.Product;
import de.seifenarts.repository.ProductRepository;
import de.seifenarts.service.interfaces.ProductService;
import de.seifenarts.service.mapping.ProductMappingService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMappingService productMappingService;

    public ProductServiceImpl(ProductRepository repository, ProductMappingService mappingService) {
        this.repository = repository;
        this.productMappingService = mappingService;
    }

    @Override
    public Long addNewProduct(ProductRequestDto productRequestDto) {
        Product product = productMappingService.mapRequestDtoToEntity(productRequestDto);

        Product savedProduct = repository.save(product);

        if (productRequestDto.getImages() != null) {
            //пока локально храним images. Надо вынести в ImageService
            Set<Image> images = productRequestDto.getImages().stream().map(file -> {
                try {
                    String uploadDir = System.getProperty("user.dir") + "/uploads/";
                    File uploadFolder = new File(uploadDir);
                    if (!uploadFolder.exists()) uploadFolder.mkdirs();

                    String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                    File dest = new File(uploadDir + filename);
                    file.transferTo(dest);

                    Image img = new Image();
                    img.setProduct(savedProduct);
                    img.setImageUrl("/" + uploadDir + filename);
                    return img;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toSet());

            savedProduct.getImages().addAll(images);
            repository.save(savedProduct);
        }

        return savedProduct.getId();
    }
}
