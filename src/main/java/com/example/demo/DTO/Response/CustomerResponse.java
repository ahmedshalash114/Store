package com.example.demo.DTO.Response;
public class CustomerResponse {
    private String name;
    private String email;
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
