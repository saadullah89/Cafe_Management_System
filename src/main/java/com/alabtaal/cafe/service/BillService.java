package com.alabtaal.cafe.service;

import com.alabtaal.cafe.entity.BillEntity;

import java.util.List;

public interface BillService {
   BillEntity save(BillEntity entity);
   List<BillEntity> findAll();
   void deleteAll();
}
