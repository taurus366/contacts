package com.contacts.www.service;

import com.contacts.www.model.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity findUserByEmail(String email);

    List<UserEntity> findAllUsers();

    Boolean deleteUser(UserEntity entity);

    UserEntity createNewUser(UserEntity entity);

    List<UserEntity> findAllByFirstNameContaining(String firstName);

    UserEntity findUserById(Long id);

    UserEntity updateExistsUser(UserEntity entity);


}
