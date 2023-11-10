package com.example.demo.Service;

import com.example.demo.Config.JwtUtils;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Exception.UserAlreadyExistsException;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.CustomUserDetails;
import com.example.demo.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;



    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    public LoginResponseDTO login(String username, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<?> roles = (List<?>) userDetails.getAuthorities();

        LoginResponseDTO res = new LoginResponseDTO();

        res.setToken(jwt);
        res.setUsername(userDetails.getUsername());
        res.setEmail(userDetails.getUsername());
        res.setId(userDetails.getId());
        res.setRoles(roles);

        return res;

    }

    public User registerUser(User user){
        validateUsernameAndEmail(user.getUsername(), user.getEmail());
        validatePassword(user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role defaultRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role does not exist"));
        user.setRoles(Collections.singleton(defaultRole));

        return userRepository.save(user);
    }

    private void validateUsernameAndEmail(String username, String email) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with given username or email already exists");
        }
    }
    private void validatePassword(String password) {
        if (password == null || !password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}")) {
            throw new IllegalArgumentException("Password does not meet the required criteria");
        }
    }
}
