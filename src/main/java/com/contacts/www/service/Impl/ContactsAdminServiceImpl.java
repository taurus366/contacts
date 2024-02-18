package com.contacts.www.service.Impl;

import com.contacts.www.exception.UserNotFoundException;
import com.contacts.www.model.entity.AdminEntity;
import com.contacts.www.repository.AdminRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ContactsAdminServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    public ContactsAdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        AdminEntity adminEntity = adminRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("Admin with username: %s doesn't exists", username)));

            return mapToUserDetails(adminEntity);
    }

    private UserDetails mapToUserDetails(AdminEntity entity) {
        return new User(entity.getUsername(), entity.getPassword(), Collections.emptyList());
    }



}
