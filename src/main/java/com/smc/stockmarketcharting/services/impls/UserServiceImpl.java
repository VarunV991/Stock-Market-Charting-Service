package com.smc.stockmarketcharting.services.impls;

import com.smc.stockmarketcharting.controllers.UserController;
import com.smc.stockmarketcharting.dtos.UserDto;
import com.smc.stockmarketcharting.mappers.UserMapper;
import com.smc.stockmarketcharting.models.User;
import com.smc.stockmarketcharting.repositories.UserRepository;
import com.smc.stockmarketcharting.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Service
public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDto addUser(UserDto userDto) throws AddressException, MessagingException {
        User user = userMapper.toUser(userDto);
        user = userRepository.save(user);
        return userMapper.toUserDto(user);
    }

    @Override
    public void sendEmail(Long userid) throws AddressException, MessagingException{
        logger.debug(Long.toString(userid));
        User user = userRepository.findById(userid).get();
        final String username = "smcroot7@gmail.com";
        final String password = "root@123";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("smcroot7@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            message.setSubject("User confirmation email");
            message.setContent(
                    "<h3><a href =\"https://smc-service.herokuapp.com/user/confirmuser/"+userid+"/\"> Click to confirm </a></h3>",
                    "text/html");
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
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
