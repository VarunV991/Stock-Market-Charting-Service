package com.smc.stockmarketcharting.services;

import com.smc.stockmarketcharting.dtos.UserDto;
import com.smc.stockmarketcharting.models.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@Service
public interface UserService {

    public UserDto addUser(UserDto userDto) throws AddressException, MessagingException;
    public void sendEmail(Long userid) throws AddressException, MessagingException;
    public String confirmationPage(Long userId);
}
