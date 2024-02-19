package com.contacts.www.service;

import com.contacts.www.model.entity.UserEntity;
import com.contacts.www.repository.UserRepository;
import com.contacts.www.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserService userService;
    @Mock
    private UserRepository mockUserRepository;

    private UserEntity testUser;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(mockUserRepository);

        //init test UserEntity
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setFirstName("fname");
        testUser.setLastName("lname");
        testUser.setPhoneNumber("1234567890");
        testUser.setEmailAddress("test@abv.bg");
        testUser.setOtherEmailAddressEntities(Collections.emptyList());
        testUser.setOtherPhoneNumberEntities(Collections.emptyList());
    }


    @Test
    void deleteUser() {



    }


}
