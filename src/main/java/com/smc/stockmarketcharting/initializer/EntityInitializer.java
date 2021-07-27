package com.smc.stockmarketcharting.initializer;

import com.smc.stockmarketcharting.models.ERole;
import com.smc.stockmarketcharting.models.Role;
import com.smc.stockmarketcharting.models.User;
import com.smc.stockmarketcharting.repositories.RoleRepository;
import com.smc.stockmarketcharting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EntityInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception
    {
        userRepository.deleteAll();
        User user = new User("admin",
                encoder.encode("admin"),
                "smcroot7@gmail.com",
                "8956238956",
                true);
        Set<Role> roles = new HashSet<>();
        Role adminRole = new Role(ERole.ROLE_ADMIN);
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
    }
}
