package com.alabtaal.cafe.service;

import com.alabtaal.cafe.entity.UserEntity;

import java.util.List;

public interface UserService  {

    UserEntity findByUsername(String username);
    UserEntity findByMobileNumber(Long mobileNumber);

    UserEntity save(UserEntity entity);
    List<UserEntity> deleteByUsernameAndPassword(String userName, String password);
    UserEntity deleteByMobileNumberAndPassword(Long mobileNumber,String password);


}
