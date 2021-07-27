package com.smc.stockmarketcharting.services.impls;

import com.sendgrid.*;
import com.smc.stockmarketcharting.controllers.UserController;
import com.smc.stockmarketcharting.dtos.UserDto;
import com.smc.stockmarketcharting.mappers.UserMapper;
import com.smc.stockmarketcharting.models.User;
import com.smc.stockmarketcharting.repositories.UserRepository;
import com.smc.stockmarketcharting.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Value("${sendgrid.api.key}")
    String sendGridApiKey;

    @Override
    public UserDto addUser(UserDto userDto){
        User user = userMapper.toUser(userDto);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void sendEmail(Long userid){
        logger.debug(Long.toString(userid));
        User user = userRepository.findById(userid).get();

        Email from = new Email("smcroot7@gmail.com");
        String subject = "User Confirmation Mail";
        Email to = new Email(user.getEmail());
        Content content = new Content("text/html",
                "<h3><a href =\"http://127.0.0.1:8080/user/confirmuser/"+userid+"/\"> Click to confirm </a></h3>");
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            logger.info(response.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String confirmationPage(Long userId){
        Optional<User> userlist =   userRepository.findById(userId);
        if(!userlist.isPresent()){
            return "User not found";
        }
        User user = userlist.get();
        user.setConfirmed(true);
        userRepository.save(user);
        return "User confirmed: " +user.getUsername();
    }

    @Override
    public String login(UserDto userDto){
        if(userDto.getUsername()=="root"){
            return "ADMIN";
        }
        else{
            return "USER";
        }
    }
}
