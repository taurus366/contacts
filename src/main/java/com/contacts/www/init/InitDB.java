package com.contacts.www.init;

import com.contacts.www.model.dto.EmailAddressDTO;
import com.contacts.www.model.dto.PhoneNumberDTO;
import com.contacts.www.model.dto.UserDTO;
import com.contacts.www.model.entity.AdminEntity;
import com.contacts.www.model.entity.EmailAddressEntity;
import com.contacts.www.model.entity.PhoneNumberEntity;
import com.contacts.www.model.entity.UserEntity;
import com.contacts.www.repository.AdminRepository;
import com.contacts.www.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InitDB implements CommandLineRunner {

    private final AdminRepository adminRepository;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    public InitDB(AdminRepository adminRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        initAdmin();
        initUsers();

    }

    private void initUsers() {
        final List<UserEntity> all = userRepository.findAll();
        System.out.println(all.size());
        if(all.isEmpty()) {

            ObjectMapper mapper = new ObjectMapper();
            Resource resource = new ClassPathResource("users_db.json");

            try {

            final List<UserDTO> list = Arrays.asList(mapper.readValue(resource.getFile(), UserDTO[].class));

                List<UserEntity> userEntityList = list
                        .stream()
                        .map(this::convertToUserEntity)
                        .toList();

               userRepository.saveAll(userEntityList);




            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }

    private UserEntity convertToUserEntity(UserDTO entity) {
        UserEntity user = new UserEntity();
        user.setEmailAddress(entity.getEmailAddress());
        user.setFirstName(entity.getFirstName());
        user.setLastName(entity.getLastName());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setOtherPhoneNumberEntities(entity.getOtherPhoneNumbers()
                .stream().map(this::convertToPhoneNumberEntity)
                .collect(Collectors.toList()));
        user.setOtherEmailAddressEntities(entity.getOtherEmailAddresses()
                .stream().map(this::convertToEmailAddressEntity)
                .collect(Collectors.toList()));
        return user;
    }
    private PhoneNumberEntity convertToPhoneNumberEntity(PhoneNumberDTO phoneNumberDTO) {
        PhoneNumberEntity number = new PhoneNumberEntity();
        number.setPhoneNumber(phoneNumberDTO.getPhoneNumber());
        return number;
    }

    private EmailAddressEntity convertToEmailAddressEntity(EmailAddressDTO emailAddressDTO) {
        EmailAddressEntity emailAddressEntity = new EmailAddressEntity();
        emailAddressEntity.setEmailAddress(emailAddressDTO.getEmailAddress());
        return emailAddressEntity;
    }

    private void initAdmin() {
        final List<AdminEntity> adminEntities = adminRepository.findAll();

        String username = "admin";
        String password = "1234";

        if(adminEntities.isEmpty()) {
            AdminEntity admin = new AdminEntity();
            admin.setUsername(username);
            admin.setPassword(passwordEncoder.encode(password));
            adminRepository.save(admin);
        }

        String green = "\u001B[32m";
        String red = "\u001B[31m";
        String resetColor = "\u001B[0m";

        System.out.printf("\n%s ADMIN INITIALIZED / USE THESE FOR LOGIN -> \n%s username: %s \n password: %s%s\n", red, green, username, password, resetColor);
    }
}
