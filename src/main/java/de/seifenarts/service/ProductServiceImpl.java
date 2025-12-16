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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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
                        Collectors.mapping(Image::getImageUrl, toList())
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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        ProductResponseDTO dto = productMappingService.mapEntityToProductResponsDTO(product);

        List<String> imageUrls = imageRepository.findAllByProductIdIn(List.of(product.getId()))
                .stream()
                .map(Image::getImageUrl)
                .toList();

        dto.setImageUrls(imageUrls);

        return dto;
    }

    @Override
    public ProductResponseDTO setProductActiveStatus(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setActive(!product.isActive());

        Product updatedProduct = productRepository.save(product);

        return productMappingService.mapEntityToProductResponsDTO(updatedProduct);
    }

    @Override
    public List<ProductResponseDTO> getRecommendedProducts(Long productId) {
        // Fetch the selected product by its ID
        Product selectedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Fetch all active products except the selected one
        List<Product> allProducts = productRepository.findAllWithImagesAndAromas().stream()
                .filter(Product::isActive)
                .filter(p -> !Objects.equals(p.getId(), selectedProduct.getId()))
                .toList();

        // 1. Products with the same price as the selected product
        List<Product> byPrice = allProducts.stream()
                .filter(p -> p.getPrice() != null && p.getPrice().compareTo(selectedProduct.getPrice()) == 0)
                .toList();

        // 2. Products with the same title as the selected product
        List<Product> byTitle = allProducts.stream()
                .filter(p -> p.getTitle() != null && p.getTitle().equalsIgnoreCase(selectedProduct.getTitle()))
                .toList();

        // 3. The remaining products (not in price or title lists)
        List<Product> others = allProducts.stream()
                .filter(p -> !byPrice.contains(p) && !byTitle.contains(p))
                .toList();

        // Combine the three lists into one stream
        List<Product> recommended = Stream.of(byPrice, byTitle, others)
                .flatMap(List::stream)
                .distinct() // remove duplicates
                .limit(30)  // optional: limit to 30 products for the recommendation carousel
                .toList();

        // Fetch images for all recommended products in one query
        Map<Long, List<String>> imagesMap = imageRepository.findAllByProductIdIn(
                        recommended.stream().map(Product::getId).toList()
                ).stream()
                .collect(Collectors.groupingBy(
                        image -> image.getProduct().getId(),
                        Collectors.mapping(Image::getImageUrl, Collectors.toList())
                ));

        // Map products to DTOs and attach image URLs
        return recommended.stream()
                .map(product -> {
                    ProductResponseDTO dto = productMappingService.mapEntityToProductResponsDTO(product);
                    dto.setImageUrls(imagesMap.getOrDefault(product.getId(), List.of()));
                    return dto;
                })
                .toList();
    }

    @Override
    public void reserveProduct(Long productId, Integer amount) {
        Product reserveProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        decreaseQuantity(reserveProduct, amount);
        setStockStatus(reserveProduct);
        productRepository.save(reserveProduct);
    }

    @Override
    public void restoreProduct(Long productId, Integer amount) {
        Product restoreProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        increaseQuantity(restoreProduct, amount);
        setStockStatus(restoreProduct);
        productRepository.save(restoreProduct);
    }


    private Product decreaseQuantity(Product product, Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if (product.getQuantity() < amount) {
            throw new IllegalStateException(
                    "Not enough quantity for product id=" + product.getId()
            );
        }
        product.setQuantity(product.getQuantity() - amount);
        return product;
    }

    private Product increaseQuantity(Product product, Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        product.setQuantity(product.getQuantity() + amount);
        return product;
    }

    private void setStockStatus(Product product) {
        if (product.getQuantity() == 0) {
            product.setStockStatus(false);
        } else {
            product.setStockStatus(true);
        }
    }
}
