package com.contacts.www.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phoneNumber;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<PhoneNumberEntity> otherPhoneNumberEntities = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL})
    private List<EmailAddressEntity> otherEmailAddressEntities = new ArrayList<>();

    @Column(nullable = false)
    private String emailAddress;

}
