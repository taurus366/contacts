package com.contacts.www.service.Impl;

import com.contacts.www.exception.ErrorMessages;
import com.contacts.www.exception.UserNotFoundException;
import com.contacts.www.model.entity.UserEntity;
import com.contacts.www.repository.UserRepository;
import com.contacts.www.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmailAddress(email).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Boolean deleteUser(UserEntity entity) {
        userRepository.delete(entity);
        return true;
    }

    @Override
    public UserEntity createNewUser(UserEntity entity) {
        return userRepository.save(entity);
    }

    @Override
    public List<UserEntity> findAllByFirstNameContaining(String firstName) {
        return userRepository.findByFirstNameContaining(firstName);
    }

    @Override
    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND));
    }

    @Override
    public UserEntity updateExistsUser(UserEntity entity) {
        return userRepository.save(entity);
    }
}
