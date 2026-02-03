package com.ex01.basic.dto;

public class MemberDto {
    private int id;
    private String username;
    private String password;
    private String role;
    public MemberDto(int id, String username, String password, String role){
        this.id =id;
        this.username=username;
        this.password=password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
