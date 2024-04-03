package com.alabtaal.cafe.service;

import com.alabtaal.cafe.entity.ItemSaleEntity;
import com.alabtaal.cafe.repository.ItemSaleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemSaleServiceImpl implements ItemSaleService{

    private final ItemSaleRepo itemSaleRepo;

    @Override
    public ItemSaleEntity save(ItemSaleEntity entity) {
        return itemSaleRepo.saveAndFlush(entity);
    }

    @Override
    public List<ItemSaleEntity> findAll() {
        return itemSaleRepo.findAll();
    }

    @Override
    public void deleteByName(String name) {
        itemSaleRepo.deleteById(findByNameIgnoreCase(name).getId());
    }

    @Override
    public ItemSaleEntity findByNameIgnoreCase(String name) {
        return itemSaleRepo.findByNameIgnoreCase(name);
    }

    @Override
    public void deleteAll() {
        itemSaleRepo.deleteAll();
    }
}
