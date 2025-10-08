package de.seifenarts.service;

import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.dto.product_dto.respons_dto.ProductResponseDTO;
import de.seifenarts.domain.entity.Image;
import de.seifenarts.domain.entity.Product;
import de.seifenarts.repository.ImageRepository;
import de.seifenarts.repository.ProductRepository;
import de.seifenarts.service.interfaces.ProductService;
import de.seifenarts.service.mapping.ProductMappingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductMappingService productMappingService;
    @Autowired
    private final ImageRepository imageRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductMappingService mappingService, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.productMappingService = mappingService;
        this.imageRepository = imageRepository;
    }

    @Override
    public Long addNewProduct(ProductRequestDto productRequestDto) {
        Product product = productMappingService.mapRequestDtoToEntity(productRequestDto);

        Product savedProduct = productRepository.save(product);

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
            productRepository.save(savedProduct);
        }

        return savedProduct.getId();
    }

    @Override
    @Transactional
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            product.setTitle(dto.getTitle());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getSize() != null) {
            product.setSize(dto.getSize());
        }
        if (dto.getDeliveryPrice() != null) {
            product.setDeliveryPrice(dto.getDeliveryPrice());
        }
        if (dto.getShortDescription() != null && !dto.getShortDescription().isBlank()) {
            product.setShortDescription(dto.getShortDescription());
        }
        if (dto.getFullDescription() != null && !dto.getFullDescription().isBlank()) {
            product.setFullDescription(dto.getFullDescription());
        }
        if (dto.getComposition() != null && !dto.getComposition().isBlank()) {
            product.setComposition(dto.getComposition());
        }

        //TODO update Aroma and Image
//        Set<Aroma> aromas = aromaRepository.findAllById(dto.getAromaIds())
//                .stream()
//                .collect(Collectors.toSet());
//        product.setAromas(aromas);

        Product updatedProduct = productRepository.save(product);

        return productMappingService.mapEntityToProductResponsDTO(updatedProduct);
    }

    @Override
    public Page<ProductResponseDTO> getAllActiveProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAllActiveProducts(pageable);
        List<Long> productIds = productsPage.stream()
                .map(Product::getId)
                .toList();

        Map<Long, List<String>> imagesMap = imageRepository.findAllByProductIdIn(productIds).stream()
                .collect(Collectors.groupingBy(
                        image -> image.getProduct().getId(),
                        Collectors.mapping(Image::getImageUrl, Collectors.toList())
                ));

        List<ProductResponseDTO> dtoList = productsPage.stream()
                .map(product -> {
                    ProductResponseDTO dto = productMappingService.mapEntityToProductResponsDTO(product);
                    dto.setImageUrls(imagesMap.getOrDefault(product.getId(), List.of()));
                    return dto;
                })
                .toList();

        return new PageImpl<>(dtoList, pageable, productsPage.getTotalElements());
    }


    @Override
    public ProductResponseDTO getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMappingService::mapEntityToProductResponsDTO)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

    }

    @Override
    public ProductResponseDTO setProductActiveStatus(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(!product.isActive());

        Product updatedProduct = productRepository.save(product);

        return productMappingService.mapEntityToProductResponsDTO(updatedProduct);
    }
}
