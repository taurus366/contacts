package com.contacts.www.service;

import com.contacts.www.exception.UserNotFoundException;
import com.contacts.www.model.entity.AdminEntity;
import com.contacts.www.repository.AdminRepository;
import com.contacts.www.service.Impl.ContactsAdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactsAdminServiceImplTest {

    ContactsAdminServiceImpl contactsAdminService;

    @Mock
    private AdminRepository adminRepository;

    private AdminEntity adminEntity;

    @BeforeEach
    void setUp() {
        contactsAdminService = new ContactsAdminServiceImpl(adminRepository);

        adminEntity = new AdminEntity();
        adminEntity.setUsername("admin");
        adminEntity.setPassword("1234"); // here should be encrypted password
    }


    @Test
    void testLoginAdminFail() {

        when(adminRepository.findByUsername(adminEntity.getUsername())).thenReturn(Optional.empty());
        assertThrows(
                UserNotFoundException.class,
                () -> contactsAdminService.loadUserByUsername(adminEntity.getUsername())
        );
    }

    @Test
    void testLoginAdminSuccess() {
        when(adminRepository.findByUsername(adminEntity.getUsername())).thenReturn(Optional.ofNullable(adminEntity));

        // Act
        UserDetails userDetails = contactsAdminService.loadUserByUsername(adminEntity.getUsername());

        // Assert
        assertEquals(adminEntity.getUsername(), userDetails.getUsername());
        assertEquals(adminEntity.getPassword(), userDetails.getPassword());

        //        verify(userService, times(1)).findByUsername("");
    }
}
