package com.contacts.www.web;

import com.contacts.www.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> getAllUsers(
            @RequestParam(name = "firstName", defaultValue = "", required = false) String firstName,
            @RequestParam(name = "pageNo", defaultValue = "0", required = false) int pageNo) {

        return ResponseEntity.ok().build();
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
