package com.alabtaal.cafe.service;

import com.alabtaal.cafe.entity.MenuManagementEntity;
import com.alabtaal.cafe.repository.MenuManagementRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuManagementServiceImpl implements MenuManagementService{

    private final MenuManagementRepo menuManagementRepo;

    @Override
    public MenuManagementEntity save(MenuManagementEntity entity) {
          return menuManagementRepo.saveAndFlush(entity);
    }

    @Override
    public MenuManagementEntity update(MenuManagementEntity entity) {
        return menuManagementRepo.save(entity);
    }

    @Override
    public void deleteById(Long id) {
        menuManagementRepo.deleteById(id);
    }
    @Transactional
    @Override
    public void deleteByName(String name) {
      menuManagementRepo.deleteById(findByNameIgnoreCase(name).getId());
    }

    @Override
    public MenuManagementEntity findByNameIgnoreCase(String name) {
        return menuManagementRepo.findByNameIgnoreCase(name);
    }

    @Override
    public List<MenuManagementEntity> findAll() {
        return menuManagementRepo.findAll();
    }
}
