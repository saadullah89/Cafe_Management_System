package com.alabtaal.cafe.repository;

import com.alabtaal.cafe.entity.BillEntity;
import com.alabtaal.cafe.entity.ItemSaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepo extends JpaRepository<BillEntity, Long> {
    
}
