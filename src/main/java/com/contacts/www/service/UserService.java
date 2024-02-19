package com.contacts.www.service;

import com.contacts.www.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    Boolean deleteUser(UserEntity entity);

    UserEntity createNewUser(UserEntity entity);

    List<UserEntity> findAllOrByFilter(String firstName, String lastName);

    UserEntity findUserById(Long id);
}
