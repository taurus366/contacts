package com.contacts.www.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone_number")
public class PhoneNumberEntity extends BaseEntity{

    @Column
    private String phoneNumber;
}
