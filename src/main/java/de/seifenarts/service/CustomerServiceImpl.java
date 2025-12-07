package de.seifenarts.service;

import de.seifenarts.domain.dto.customer_dto.request_dto.CustomerRequestDto;
import de.seifenarts.domain.entity.Customer;
import de.seifenarts.repository.CustomerRepository;
import de.seifenarts.service.interfaces.CustomerService;
import de.seifenarts.service.mapping.CustomerMappingService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMappingService mappingService;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMappingService mappingService) {
        this.repository = repository;
        this.mappingService = mappingService;
    }

    @Override
    public Long addNewCustomer(CustomerRequestDto dto) {

        Customer existing = repository.findByEmail(dto.getEmail()).orElse(null);

        if (existing != null) {
            return existing.getId();
        }

        Customer newCustomer = mappingService.mapRequestDtoToEntity(dto);

        Customer saved = repository.save(newCustomer);

        return saved.getId();
    }
}
