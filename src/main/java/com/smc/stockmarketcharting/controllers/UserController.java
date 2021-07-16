package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.UserDto;
import com.smc.stockmarketcharting.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody UserDto userDto) throws AddressException, MessagingException {
        UserDto user = userService.addUser(userDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Responded","UserController");
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        userService.sendEmail(user.getId());
        return user.toString();
    }

    @GetMapping(value="/confirmuser/{userid}")
    public String confirmationPage(@PathVariable(value = "userid") Long userId) {
        return userService.confirmationPage(userId);
    }

}
