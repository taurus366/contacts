package com.contacts.www.web;

import com.contacts.www.model.entity.UserEntity;
import com.contacts.www.repository.UserRepository;
import com.contacts.www.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
//    private UserService userService;


    @Autowired
    private UserRepository userRepository;

    private UserEntity saveduserEntity;

    @Autowired
    private ObjectMapper objectMapper;

//    @MockBean
//    private UserService userService;

    @BeforeEach
    void setUp() {

//        userService = new UserServiceImpl(userRepository);
        //init test UserEntity
       UserEntity userEntity = new UserEntity();
        userEntity.setId(10L);
        userEntity.setFirstName("fname");
        userEntity.setLastName("lname");
        userEntity.setPhoneNumber("1234567890");
        userEntity.setEmailAddress("test@abv.bg");

        userRepository.deleteAll(); // use this because on starting the app init.class creates new records.

        saveduserEntity = userRepository.save(userEntity);
    }

    @AfterEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "admin")
    void testRemoveUserById() throws Exception {
        mockMvc
                .perform(delete("/user/remove/{id}", saveduserEntity.getId()))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "admin")//
    void testGetUserById() throws Exception {
        mockMvc
                .perform(get("/user/get/{id}",saveduserEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saveduserEntity.getId()))
                .andExpect(jsonPath("$.firstName").value(saveduserEntity.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(saveduserEntity.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(saveduserEntity.getPhoneNumber()))
                .andExpect(jsonPath("$.emailAddress").value(saveduserEntity.getEmailAddress()))
                .andExpect(jsonPath("$.otherPhoneNumbers").value(nullValue()))
                .andExpect(jsonPath("$.otherEmailAddresses").value(nullValue()));
    }

    @Test
    @WithMockUser(username = "admin")//
    void testGetAllUsers() throws Exception {
        mockMvc
                .perform(get("/user/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @WithMockUser(username = "admin")
    void testGetUserByFilter() throws Exception {
        mockMvc
                .perform(get("/user/get/all")
                        .param("first_name", "fname1")
                        .param("last_name", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser(username = "admin")//
    void testGetUserByFilter2() throws Exception {
        mockMvc
                .perform(get("/user/get/all")
                        .param("first_name", saveduserEntity.getFirstName())
                        .param("last_name", saveduserEntity.getLastName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(saveduserEntity.getId()));
    }

    @Test
    @WithMockUser(username = "admin")//
    void testUpdateUserData() throws Exception {
        saveduserEntity.setFirstName("fname2");
        saveduserEntity.setLastName("lname2");


        String updatedUserJson = objectMapper.writeValueAsString(saveduserEntity);

        mockMvc
                .perform(put("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isOk());

        //        verify(userService, times(1)).createNewUser(userEntity);
    }

    @Test
    @WithMockUser(username = "admin")
    void testUpdateUserDataException() throws Exception {
        saveduserEntity.setFirstName("");

        String updatedUserJson = objectMapper.writeValueAsString(saveduserEntity);

        mockMvc
                .perform(put("/user/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUserJson))
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(username = "admin")
    void testCreateUser() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("testf");
        userEntity.setLastName("testl");
        userEntity.setPhoneNumber("1234567");
        userEntity.setEmailAddress("test2@abv.bg");

        String createdUserJson = objectMapper.writeValueAsString(userEntity);
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdUserJson))
                .andExpect(status().isOk());

//        verify(userService, times(1)).createNewUser(userEntity);
    }

    @Test
    @WithMockUser(username = "admin")
    void testCreateUserException() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("2");
        userEntity.setLastName("testlname");
        userEntity.setPhoneNumber("1234567");
        userEntity.setEmailAddress("test2@abv.bg");

        String createdUserJson = objectMapper.writeValueAsString(userEntity);
        mockMvc
                .perform(post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createdUserJson))
                .andExpect(status().isConflict())
//                .andExpect(jsonPath("$.[0].codes").value(Matchers.containsInAnyOrder("NotBlank.userBindingModel.firstName", "NotBlank.firstName", "NotBlank.java.lang.String", "NotBlank", "Size.userBindingModel.firstName")))
                .andExpect(jsonPath("$.[0].codes").value(Matchers.containsInAnyOrder("Size.userBindingModel.firstName", "Size.firstName", "Size.java.lang.String", "Size")))
                .andExpect(jsonPath("$.[0].defaultMessage").value("first name must be min >= 3"));
    }

}
