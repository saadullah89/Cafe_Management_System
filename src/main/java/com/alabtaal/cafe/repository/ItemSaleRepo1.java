package com.alabtaal.cafe.repository;

import com.alabtaal.cafe.entity.ItemSaleEntity;
import com.alabtaal.cafe.entity.ItemSaleEntity1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepo1 extends JpaRepository<ItemSaleEntity1, Long> {
}
