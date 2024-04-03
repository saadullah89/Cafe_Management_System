package com.alabtaal.cafe.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "cafe" , name = "users" )
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


}
