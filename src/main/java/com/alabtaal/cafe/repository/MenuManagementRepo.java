package com.alabtaal.cafe.repository;

import com.alabtaal.cafe.entity.MenuManagementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuManagementRepo extends JpaRepository<MenuManagementEntity,Long> {

    MenuManagementEntity findByNameIgnoreCase(String name);

}
