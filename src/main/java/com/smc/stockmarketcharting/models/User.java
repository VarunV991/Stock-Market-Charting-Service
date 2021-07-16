package com.smc.stockmarketcharting.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Id
    private long id;
    private String username;
    private String password;
    private String type;
    private String email;
    private String mobileNumber;
    private boolean confirmed;
}
