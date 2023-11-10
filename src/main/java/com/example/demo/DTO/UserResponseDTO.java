package com.example.demo.DTO;

public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;

    public Long getId(){ return this.id; }
    public String getUsername(){ return this.username; }
    public String getEmail(){ return this.email; }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUsername(String username){this.username=username;}
    public void setEmail(String email){this.email=email;}
}
