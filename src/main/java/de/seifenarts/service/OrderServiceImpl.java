package de.seifenarts.service;

import de.seifenarts.domain.dto.OrderProduct.request_order_product_dto.OrderProductRequest;
import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.order_dto.response_dto.OrderResponseDto;
import de.seifenarts.domain.dto.product_dto.request_dto.ProductRequestDto;
import de.seifenarts.domain.dto.product_dto.respons_dto.ProductResponseDTO;
import de.seifenarts.domain.entity.*;
import de.seifenarts.repository.CustomerRepository;
import de.seifenarts.repository.OrderRepository;
import de.seifenarts.repository.ProductRepository;
import de.seifenarts.service.interfaces.OrderService;
import de.seifenarts.service.interfaces.ProductService;
import de.seifenarts.service.mapping.OrderMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderMappingService orderMappingService;
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMappingService orderMappingService, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMappingService = orderMappingService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Long addNewOrder(OrderRequestDto dto) {
        Order order = orderMappingService.mapRequestDtoToEntity(dto);

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.CREATED);
        }

        dto.getProducts().forEach(reg -> addOrderProduct(order, reg));

        order.setTotalPrice(calculateOrderTotal(order));

        orderRepository.save(order);

        return order.getId();

    }

    private void addOrderProduct(Order order, OrderProductRequest req) {
        Product product = productRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + req.getId()));
        OrderProduct op = new OrderProduct();
        op.setOrder(order);
        op.setProduct(product);
        op.setQuantity(req.getQuantity());
        op.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(req.getQuantity())));
        order.getOrderProducts().add(op);
    }

    private BigDecimal calculateOrderTotal(Order order) {
        return order.getOrderProducts().stream()
                .map(OrderProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));


        if (dto.getStatus() != null) {
            order.setStatus(dto.getStatus());
        }

        if (dto.getCity() != null && !dto.getCity().isBlank()) {
            order.setCity(dto.getCity());
        }

        if (dto.getFirstName() != null) {
            order.setFirstName(dto.getFirstName());
        }

        if (dto.getLastName() != null) {
            order.setLastName(dto.getLastName());
        }

        if (dto.getHouseNumber() != null) {
            order.setHouseNumber(dto.getHouseNumber());
        }

        if (dto.getStreet() != null) {
            order.setStreet(dto.getStreet());
        }

        if (dto.getZipCode() != null) {
            order.setZipCode(dto.getZipCode());
        }

        Order savedOrder = orderRepository.save(order);

        return orderMappingService.mapEntityToResponseDto(savedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {

        if (!orderRepository.existsById(orderId)) {
            throw new RuntimeException("Order not found");
        }

        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMappingService.mapEntityToResponseDto(order);
    }
}
