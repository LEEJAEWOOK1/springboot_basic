package com.ex01.basic.service;

import com.ex01.basic.exception.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class MemberFileService {
    private static final String DIR ="uploads/";
    public String saveFile(MultipartFile multipartFile){
        String fileName = null;
        if(multipartFile == null || multipartFile.isEmpty())
            fileName = "nan";
        else{
            fileName = UUID.randomUUID().toString() + "-" //UUID : 전세계적으로 유일한 값을 뽑아낸다.
                        + multipartFile.getOriginalFilename();
            Path path = Paths.get(DIR + fileName);
            try {
                Files.createDirectories(path.getParent()); //폴더 생성
                multipartFile.transferTo(path); //저장
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return fileName; //변경되어 있는 파일이름 return
    }
    public byte[] getImage(String fileName){
        Path filePath = Paths.get(DIR + fileName);
        if(!Files.exists(filePath))
            throw new MemberNotFoundException("파일이 존재하지 않음");
        byte[] imageBytes = {0};
        try {
            imageBytes = Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageBytes;
    }
    public void deleteFile(String fileName){
        Path path = Paths.get(DIR + fileName);
        try{
            Files.deleteIfExists(path);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
