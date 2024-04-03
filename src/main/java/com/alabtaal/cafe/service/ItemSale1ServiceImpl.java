package com.alabtaal.cafe.service;


import com.alabtaal.cafe.entity.ItemSaleEntity1;
import com.alabtaal.cafe.repository.ItemSaleRepo1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class ItemSale1ServiceImpl implements ItemSale1Service{

    private final ItemSaleRepo1 saleRepo1;

    @Override
    public ItemSaleEntity1 save(ItemSaleEntity1 entity) {
        return saleRepo1.saveAndFlush(entity);
    }

    @Override
    public List<ItemSaleEntity1> findAll() {
        return saleRepo1.findAll();
    }
}
