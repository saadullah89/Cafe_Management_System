package com.alabtaal.cafe.service;


import com.alabtaal.cafe.entity.CustomerEntity;
import com.alabtaal.cafe.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepo customerRepo;

    @Override
    public CustomerEntity save(CustomerEntity entity) {
        return customerRepo.saveAndFlush(entity);
    }

    @Override
    public List<CustomerEntity> findAll() {
        return customerRepo.findAll();
    }
}
