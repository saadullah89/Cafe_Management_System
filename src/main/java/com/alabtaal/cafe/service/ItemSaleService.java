package com.alabtaal.cafe.service;

import com.alabtaal.cafe.entity.ItemSaleEntity;

import java.util.List;

public interface ItemSaleService {

    ItemSaleEntity save(ItemSaleEntity entity);
    List<ItemSaleEntity> findAll();
    void deleteByName(String name);
    ItemSaleEntity findByNameIgnoreCase(String name);

    void deleteAll();
}
