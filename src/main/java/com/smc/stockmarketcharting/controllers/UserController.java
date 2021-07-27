package com.smc.stockmarketcharting.controllers;

import com.smc.stockmarketcharting.dtos.JwtResponseDto;
import com.smc.stockmarketcharting.dtos.UserDto;
import com.smc.stockmarketcharting.models.ERole;
import com.smc.stockmarketcharting.models.Role;
import com.smc.stockmarketcharting.models.User;
import com.smc.stockmarketcharting.repositories.RoleRepository;
import com.smc.stockmarketcharting.repositories.UserRepository;
import com.smc.stockmarketcharting.security.jwt.JwtUtils;
import com.smc.stockmarketcharting.security.services.UserDetailsImpl;
import com.smc.stockmarketcharting.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody UserDto userDto) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            if (userDetails.isConfirmed()) {
                String role = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList()).get(0);

                return ResponseEntity.ok(new JwtResponseDto(jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getMobileNumber(),
                        role));
            }
            return ResponseEntity.status(401).body(
                    "The user has not yet been confirmed. " +
                            "Please check your registered email to confirm your account.");
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found. Please enter valid " +
                    "credentials.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: Email is already in use!");
        }

        User user = new User(userDto.getUsername(),
                encoder.encode(userDto.getPassword()),
                userDto.getEmail(),
                userDto.getMobileNumber(),
                false);

        String role = userDto.getRole();
        Set<Role> roles = new HashSet<>();

        if (role == null || role.equals("user")) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                 .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
        }
        user.setRoles(roles);
        userRepository.save(user);

        try{
            userService.sendEmail(user.getId());
        }
        catch (Exception e){
            user.setConfirmed(true);
            userRepository.save(user);
        }
        return ResponseEntity.ok("User registered successfully!");
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody UserDto userDto){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

            User user = userRepository.findByUsername(userDto.getUsername()).orElse(null);
            if(userDto.getNewPassword()!=null && user!=null){
                user.setPassword(encoder.encode(userDto.getNewPassword()));
                user = userRepository.save(user);
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getNewPassword()));
            }
            user.setMobileNumber(userDto.getMobileNumber());
            userRepository.save(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String role = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList()).get(0);

            return ResponseEntity.ok(new JwtResponseDto(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    userDetails.getMobileNumber(),
                    role));
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid Credentials");
        }
    }

    @GetMapping(value="/confirmuser/{userid}")
    public String confirmationPage(@PathVariable(value = "userid") Long userId) {
        return userService.confirmationPage(userId);
    }
}
