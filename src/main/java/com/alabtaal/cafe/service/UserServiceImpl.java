package com.alabtaal.cafe.service;

import com.alabtaal.cafe.entity.UserEntity;
import com.alabtaal.cafe.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserEntity findByMobileNumber(Long mobileNumber) {
        return userRepo.findByMobileNumber(mobileNumber);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return userRepo.saveAndFlush(entity);
    }
    @Override
    public List<UserEntity> deleteByUsernameAndPassword(String userName, String password) {
        return userRepo.deleteByUsernameAndPassword(userName,password);
    }
    @Override
    public UserEntity deleteByMobileNumberAndPassword(Long mobileNumber, String password) {
        return userRepo.deleteByMobileNumberAndPassword(mobileNumber,password);
    }

}
