package com.alabtaal.cafe.service;


import com.alabtaal.cafe.entity.ItemSaleEntity1;

import java.util.List;

public interface ItemSale1Service {
   ItemSaleEntity1 save(ItemSaleEntity1 entity);
   List<ItemSaleEntity1> findAll();

}
