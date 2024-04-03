package com.alabtaal.cafe.service;


import com.alabtaal.cafe.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
   CustomerEntity save(CustomerEntity entity);
   List<CustomerEntity> findAll();

}
