package com.example.demo.Controller;

import com.example.demo.DTO.LoginRequestDTO;
import com.example.demo.DTO.LoginResponseDTO;
import com.example.demo.DTO.UserRegisterRequestDTO;
import com.example.demo.DTO.UserResponseDTO;
import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRegisterRequestDTO userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(userDto.getPassword());
        newUser.setEmail(userDto.getEmail());

        User registeredUser = userService.registerUser(newUser);

        UserResponseDTO responseDto = new UserResponseDTO();
        responseDto.setId(registeredUser.getId());
        responseDto.setUsername(registeredUser.getUsername());
        responseDto.setEmail(registeredUser.getEmail());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest){
        LoginResponseDTO responseDto = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
