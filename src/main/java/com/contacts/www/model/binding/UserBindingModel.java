package com.contacts.www.model.binding;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserBindingModel {
// at this situation I only check for empty fields.
//    I can add custom validation also.
    private Long id;

    @NotNull
    @NotBlank(message = "must be written first name")
    @Size(min = 3, message = "first name must be min >= 3")
    private String firstName;

    @NotBlank(message = "must be written last name")
    @Size(min = 5, max = 10, message = "last name must be min >=5 max <= 10")
    private String lastName;

    @NotBlank(message = "must be written email")
    private String emailAddress;

    @NotBlank(message = "must be written phone")
    private String phoneNumber;

}
