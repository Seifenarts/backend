package de.seifenarts.service;

import de.seifenarts.domain.composite_key.OrderProductId;
import de.seifenarts.domain.dto.orderProduct_dto.request_order_product_dto.OrderProductRequest;
import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.order_dto.response_dto.OrderResponseDto;
import de.seifenarts.domain.entity.*;
import de.seifenarts.repository.CustomerRepository;
import de.seifenarts.repository.OrderProductRepository;
import de.seifenarts.repository.OrderRepository;
import de.seifenarts.repository.ProductRepository;
import de.seifenarts.service.interfaces.CustomerService;
import de.seifenarts.service.interfaces.OrderService;
import de.seifenarts.service.mapping.OrderMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMappingService orderMappingService;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMappingService orderMappingService, CustomerRepository customerRepository, ProductRepository productRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.orderMappingService = orderMappingService;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;

    }

    @Override
    public Long addNewOrder(OrderRequestDto dto) {

        Order unsaved = orderMappingService.mapRequestDtoToEntity(dto);

        Long customerId = customerService.addNewCustomer(dto.getCustomer());
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        unsaved.setCustomer(customer);
        unsaved.setFirstName(dto.getCustomer().getFirstName());
        unsaved.setLastName(dto.getCustomer().getLastName());
        unsaved.setStreet(dto.getCustomer().getStreet());
        unsaved.setHouseNumber(dto.getCustomer().getHouseNumber());
        unsaved.setZipCode(dto.getCustomer().getZipCode());
        unsaved.setCity(dto.getCustomer().getCity());
        unsaved.setDeliveryMethod(dto.getCustomer().getDeliveryMethod());

        unsaved.setCreatedAt(LocalDateTime.now());

        if (unsaved.getStatus() == null) {
            unsaved.setStatus(OrderStatus.CREATED);
        }
        unsaved.setTotalPrice(BigDecimal.ZERO);

        if (dto.getProducts() == null || dto.getProducts().isEmpty()) {
            throw new RuntimeException("Order must contain at least one product");
        }

        Order savedOrder = orderRepository.save(unsaved);

        dto.getProducts().forEach(req -> addOrderProduct(savedOrder, req));

        savedOrder.setTotalPrice(calculateOrderTotal(savedOrder));

        orderRepository.save(savedOrder);
        return savedOrder.getId();
    }

    private void addOrderProduct(Order order, OrderProductRequest req) {

        Product product = productRepository.findById(req.getId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + req.getId()));

        OrderProductId id = new OrderProductId(order.getId(), product.getId());

        OrderProduct op = new OrderProduct();
        op.setId(id);
        op.setOrder(order);
        op.setProduct(product);
        op.setQuantity(req.getQuantity());
        BigDecimal itemPrice = product.getPrice();

        if (order.getDeliveryMethod() == DeliveryMethod.DELIVERY) {
            itemPrice = itemPrice.add(product.getDeliveryPrice());
        }
        op.setTotalPrice(itemPrice.multiply(BigDecimal.valueOf(req.getQuantity())));

        order.getOrderProducts().add(op);
    }

    private BigDecimal calculateOrderTotal(Order order) {
        return order.getOrderProducts().stream()
                .map(OrderProduct::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


//    @Override
//    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto dto) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//
//        if (dto.getStatus() != null) {
//            order.setStatus(dto.getStatus());
//        }
//
//        if (dto.getCity() != null && !dto.getCity().isBlank()) {
//            order.setCity(dto.getCity());
//        }
//
//        if (dto.getFirstName() != null) {
//            order.setFirstName(dto.getFirstName());
//        }
//
//        if (dto.getLastName() != null) {
//            order.setLastName(dto.getLastName());
//        }
//
//        if (dto.getHouseNumber() != null) {
//            order.setHouseNumber(dto.getHouseNumber());
//        }
//
//        if (dto.getStreet() != null) {
//            order.setStreet(dto.getStreet());
//        }
//
//        if (dto.getZipCode() != null) {
//            order.setZipCode(dto.getZipCode());
//        }
//
//        Order savedOrder = orderRepository.save(order);
//
//        return orderMappingService.mapEntityToResponseDto(savedOrder);
//    }

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
