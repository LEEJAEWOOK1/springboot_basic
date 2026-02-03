package com.ex01.basic.controller;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberDuplicateException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MemberAPI", description = "회원 도메인 API")
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
    @Operation(
            summary = "전체 회원 조회"
    )
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
    @Operation(
            summary = "특정 회원 조회"
    )
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
    @Operation(
            summary = "회원 수정"
    )
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
    @Operation(
            summary = "회원 삭제"
    )
    public ResponseEntity<Void> deleteMember(@PathVariable("id") int id){
        try {
            memberService.delMember( id );
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    @Operation(
            summary = "회원 추가"
    )
    public ResponseEntity<String> register(@ModelAttribute MemberDto memberDto){
        try {
            memberService.insert(memberDto);
        } catch (MemberDuplicateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("동일 id 존재");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }
}
