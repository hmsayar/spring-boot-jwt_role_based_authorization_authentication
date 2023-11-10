package com.example.demo.DTO;

public class UserRegisterRequestDTO {

    private String username;
    private String password;
    private String email;


    public String getUsername(){ return this.username; }
    public String getEmail(){ return this.email; }
    public String getPassword(){ return this.password; }

    public void setPassword(String password){this.password=password; }
    public void setUsername(String username){this.username=username;}
    public void setEmail(String email){this.email=email;}
}
