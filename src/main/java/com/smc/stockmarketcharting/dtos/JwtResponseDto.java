package com.smc.stockmarketcharting.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDto {
    private String token;
    private String type = "Bearer";
    private long id;
    private String username;
    private String password;
    private String email;
    private String mobileNumber;
    private String role;

    public JwtResponseDto(String accessToken, Long id, String username,
                          String email,String mobileNumber, String role) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.role = role;
    }

}
