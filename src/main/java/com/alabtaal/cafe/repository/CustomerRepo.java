package com.alabtaal.cafe.repository;

import com.alabtaal.cafe.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity , Long> {
}
