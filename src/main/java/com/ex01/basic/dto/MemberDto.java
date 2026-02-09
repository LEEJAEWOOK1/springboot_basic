package com.ex01.basic.dto;

import com.ex01.basic.entity.MemberEntity;
import org.springframework.beans.BeanUtils;

public class MemberDto {
    private int id;
    private String username;
    private String password;
    private String role;
    private String fileName;

    public MemberDto() {}

    public MemberDto(int id, String username, String password, String role, String fileName){
        this.id =id;
        this.username=username;
        this.password=password;
        this.role = role;
        this.fileName = fileName;
    }
    public MemberDto(MemberEntity memberEntity){
        BeanUtils.copyProperties(memberEntity, this);
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
