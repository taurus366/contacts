package com.contacts.www.model.dto;

import com.contacts.www.model.entity.EmailAddress;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phoneNumber;

    private List<PhoneNumberDTO> otherPhoneNumbers;

    List<EmailAddressDTO> otherEmailAddresses;
}
