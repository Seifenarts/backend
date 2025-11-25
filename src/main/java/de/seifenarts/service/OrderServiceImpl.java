package de.seifenarts.service;

import de.seifenarts.domain.dto.order_dto.request_dto.OrderRequestDto;
import de.seifenarts.domain.dto.order_dto.response_dto.OrderResponseDto;
import de.seifenarts.domain.entity.Customer;
import de.seifenarts.domain.entity.Order;
import de.seifenarts.domain.entity.OrderStatus;
import de.seifenarts.repository.CustomerRepository;
import de.seifenarts.repository.OrderRepository;
import de.seifenarts.service.interfaces.OrderService;
import de.seifenarts.service.mapping.OrderMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderMappingService orderMappingService;
    @Autowired
    private final CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMappingService orderMappingService, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderMappingService = orderMappingService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Long addNewOrder(OrderRequestDto orderRequestDto) {
        Order order = orderMappingService.mapRequestDtoToEntity(orderRequestDto);
        Customer customer = customerRepository.findById(orderRequestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.CREATED);
        }

        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (dto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            order.setCustomer(customer);
        }

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
