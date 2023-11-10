package com.example.demo.DTO;

import java.util.List;

public class LoginResponseDTO {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private Long id;

    private List<?> roles;

    public List<?> getRoles() {
        return roles;
    }

    public void setRoles(List<?> roles) {
        this.roles = roles;
    }

    public String getToken(){ return this.token;}
    public String getType(){ return this.type; }

    public String getUsername() {
        return this.username;
    }

    public Long getId() {
        return this.id;
    }
    public String getEmail(){
        return this.email;
    }

    public void setUsername(String username){ this.username=username;}
    public void setEmail(String email){ this.email=email;}
    public void setId(Long id){this.id=id;}
    public void setToken(String token){ this.token=token; }
    public void setType(String type){ this.type=type; }
}
