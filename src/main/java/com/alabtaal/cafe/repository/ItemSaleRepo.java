package com.alabtaal.cafe.repository;

import com.alabtaal.cafe.entity.ItemSaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepo extends JpaRepository<ItemSaleEntity , Long> {
    ItemSaleEntity findByNameIgnoreCase(String name);
}
