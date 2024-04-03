package com.alabtaal.cafe.service;


import com.alabtaal.cafe.entity.MenuManagementEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface MenuManagementService {

    MenuManagementEntity save(MenuManagementEntity entity);
    MenuManagementEntity update(MenuManagementEntity entity);
     void deleteById(Long id);
    void deleteByName(String name);

    MenuManagementEntity findByNameIgnoreCase(String name);

    List<MenuManagementEntity> findAll();
}
