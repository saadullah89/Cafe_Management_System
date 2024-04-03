package com.alabtaal.cafe.service;


import com.alabtaal.cafe.entity.BillEntity;
import com.alabtaal.cafe.repository.BillRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor


public class BillServiceImpl implements BillService{
    private final BillRepo billRepo;


    @Override
    public BillEntity save(BillEntity entity) {
        return billRepo.save(entity);
    }

    @Override
    public List<BillEntity> findAll() {
        return billRepo.findAll();
    }

    @Override
    public void deleteAll() {
        billRepo.deleteAll();
    }
}
