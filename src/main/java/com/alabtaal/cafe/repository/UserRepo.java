package com.alabtaal.cafe.repository;

import com.alabtaal.cafe.entity.UserEntity;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository <UserEntity,Long> {

    UserEntity findByUsername(String username);
    UserEntity findByMobileNumber(Long mobileNumber);


   // Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPassword(String password);

    List<UserEntity> deleteByUsernameAndPassword(String userName, String password);
    UserEntity deleteByMobileNumberAndPassword(Long mobileNumber,String password);
}
