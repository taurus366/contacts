package com.contacts.www.web;

import com.contacts.www.model.dto.EmailAddressDTO;
import com.contacts.www.model.dto.PhoneNumberDTO;
import com.contacts.www.model.dto.UserDTO;
import com.contacts.www.model.entity.UserEntity;
import com.contacts.www.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // Here I can check is admin logged yet , in this situation it's not necessary because I have configured spring to prevent use them "/**"
    // Spring configure can be found on config/ApplicationSecurityConfiguration
    // Here can be written custom check for example : isAdmin, hasSpecificRole , etc..
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<Object> addNewUser() {

        return ResponseEntity.ok().build();
    }


    @PutMapping("/edit")
    public ResponseEntity<Object> updateUserData() {

        return ResponseEntity.ok().build();
    }


    @GetMapping("/get/all")
    public ResponseEntity<Object> getAllUsersOrFilter(
            @RequestParam(name = "first_name", defaultValue = "", required = false) String firstName,
            @RequestParam(name = "last_name", defaultValue = "", required = false) String lastName) {

        final List<UserEntity> allOrByFilter = userService.findAllOrByFilter(firstName, lastName);

        List<UserDTO> userDTOList = allOrByFilter.stream()
                .map(userEntity -> {
                  UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
                  userDTO.setOtherEmailAddresses(
                          userEntity.getOtherEmailAddressEntities()
                                  .stream()
                                  .map(emailAddressEntity -> modelMapper.map(emailAddressEntity, EmailAddressDTO.class))
                                  .toList());
                  userDTO.setOtherPhoneNumbers(
                          userEntity.getOtherPhoneNumberEntities()
                                  .stream()
                                  .map(phoneNumberEntity -> modelMapper.map(phoneNumberEntity, PhoneNumberDTO.class))
                                  .toList());
                  return userDTO;
                })
                .toList();

        return ResponseEntity.ok(userDTOList);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Object> removeUserById(@PathVariable Long id) {

        return ResponseEntity.ok().build();
    }




}
