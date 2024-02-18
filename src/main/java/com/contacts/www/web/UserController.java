package com.contacts.www.web;

import com.contacts.www.exception.UserNotFoundException;
import com.contacts.www.model.binding.UserBindingModel;
import com.contacts.www.model.dto.EmailAddressDTO;
import com.contacts.www.model.dto.PhoneNumberDTO;
import com.contacts.www.model.dto.UserDTO;
import com.contacts.www.model.entity.UserEntity;
import com.contacts.www.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public ResponseEntity<Object> addNewUser(@Valid @RequestBody UserBindingModel userBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userBindingModel", userBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userBindingModel");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }


        final UserEntity mapped = modelMapper.map(userBindingModel, UserEntity.class);

        userService.createNewUser(mapped);

        return ResponseEntity.ok().build();
    }


    @PutMapping("/edit")
    public ResponseEntity<Object> updateUserData(@Valid @RequestBody UserBindingModel userBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("userBindingModel", userBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userBindingModel");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(bindingResult.getAllErrors());
        }

        final UserEntity mapped = modelMapper.map(userBindingModel, UserEntity.class);


        final UserEntity userById = userService.findUserById(mapped.getId());
        userById.setFirstName(mapped.getFirstName());
        userById.setLastName(mapped.getLastName());
        userById.setEmailAddress(mapped.getEmailAddress());
        userById.setPhoneNumber(mapped.getPhoneNumber());

            userService.createNewUser(userById);

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


        final UserEntity userById = userService.findUserById(id);

        final UserDTO userDTO = modelMapper.map(userById, UserDTO.class);

        return ResponseEntity.ok(userDTO);
    }


    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Object> removeUserById(@PathVariable Long id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        I can set just id for delete instead first finding by id, So I decided to use this.
        try {
            final UserEntity userById = userService.findUserById(id);
            userService.deleteUser(userById);
        } catch (UserNotFoundException e){
            System.out.printf("[%s] - error not found: user: %s%n", LocalDateTime.now().format(formatter), id);
        }


        return ResponseEntity.ok().build();
    }




}
