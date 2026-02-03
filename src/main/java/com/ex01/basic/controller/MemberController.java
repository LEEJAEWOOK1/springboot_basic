package com.ex01.basic.controller;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberDuplicateException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    /*
    public MemberController(){
        System.out.println("member ctrl 생성자");
    }
     */
    @GetMapping("test")
    public ResponseEntity<String> getTest(){
        memberService.serviceTest();
        System.out.println("service : " + memberService);
        return ResponseEntity.ok("성공");
    }
    @GetMapping
    public ResponseEntity<List<MemberDto>> getList(){
        List<MemberDto>list = null;
        try {
           list = memberService.getList();
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        }
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}") // /members/{id}
    public ResponseEntity<MemberDto> getOne(@PathVariable("id") int id){
        MemberDto memberDto = null;
        //System.out.println("연결 확인 : "+id);
        try {
            memberDto = memberService.getOne(id);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        //select * from member where id={id}
        return ResponseEntity.ok(memberDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@ModelAttribute MemberDto memberDto,
                                        @PathVariable("id") int id){
        //System.out.println("연결 확인 : "+id);
        try {
            memberService.modify(id, memberDto);
        } catch (MemberNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        //return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("id") int id){
        try {
            memberService.delMember( id );
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<String> register(@ModelAttribute MemberDto memberDto){
        try {
            memberService.insert(memberDto);
        } catch (MemberDuplicateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일 id 존재");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }
}
