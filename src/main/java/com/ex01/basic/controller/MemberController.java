package com.ex01.basic.controller;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
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
    @PostMapping("/login") //members/login
    @Operation(
            summary = "로그인",
            description = "username과 password 인증"
    )
    @ApiResponses({ //API 응답(상태코드, 설명)을 Swagger에 정의
            //성공
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content( //요청/응답 바디의 내용을 설명
                            mediaType = "application/json", // 응답형식 지정
                            schema = @Schema(implementation = Integer.class, example = "0")
                    )
            ),
            //사용자 없음(실패)
            @ApiResponse(responseCode = "401", description = "아이디 또는 비번 틀림",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class, example = "1")
                    )
            )
    })
    public ResponseEntity<Integer> login(@RequestBody LoginDto loginDto){
        System.out.println("loginDto => " + loginDto);
        //try {
            memberService.login(loginDto);
        //} catch (InvalidLoginException e){
           // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(1);
        //}
        return ResponseEntity.ok(0);
    }
    @GetMapping("test")
    public ResponseEntity<String> getTest(){
        memberService.serviceTest();
        System.out.println("service : " + memberService);
        return ResponseEntity.ok("성공");
    }
    @GetMapping
    @Operation(
            summary = "전체 회원 조회",
            description = "회원 목록 조회"
    )
    @ApiResponses({ //API 응답(상태코드, 설명)을 Swagger에 정의
            //성공
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content( //요청/응답 바디의 내용을 설명
                            mediaType = "application/json", // 응답형식 지정
                            schema = @Schema(implementation = MemberDto[].class, example = """
                                    [
                                        {
                                            "id" : 1,
                                            "username" : "aaa",
                                            "password" : "111",
                                            "role" : "USER"
                                        }
                                    ]
                                    """)
                            /*
                            array = @ArraySchema(//배열 형태의 데이터 구조를 설명
                                    schema = @Schema(implementation = MemberDto.class) // 이 데이터의 실제 모델 클래스는 뭐다
                            ) //API요청/응답에 사용되는 데이터 모델의 구조를 설명
                            */

                    )
            ),
            //사용자 없음(실패)
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="null") //Swagger에서 보여줄 예시데이터
                    )
            )
    })
    public ResponseEntity<List<MemberDto>> getList(
            @RequestParam(name="start", defaultValue = "0") int start){
        System.out.println("start : " +start);
        List<MemberDto>list = null;
        //try {
           list = memberService.getList(start);
        //} catch (MemberNotFoundException e) {
         //   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        //}
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}") // /members/{id}
    @Operation(
            summary = "특정 회원 조회",
            description = "사용자의 id를 파라미터로 전달"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="null")
                    )
            )
    })
    public ResponseEntity<MemberDto> getOne(@PathVariable("id") int id){
        MemberDto memberDto = null;
        //System.out.println("연결 확인 : "+id);
        //try {
            memberDto = memberService.getOne(id);
        //} catch (MemberNotFoundException e){
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        //}
        //select * from member where id={id}
        return ResponseEntity.ok(memberDto);
    }
    @PutMapping("/{id}")
    @Operation(
            summary = "회원 수정",
            description = "사용자를 수정 합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class) // void.class: void를 클래스 형태로 표현, 응답 바디가 없다
                    )
            ),
            @ApiResponse(responseCode = "404", description = "수정 사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            )
    })
    public ResponseEntity<Void> update(@ParameterObject @ModelAttribute MemberDto memberDto,
                                        @PathVariable("id") int id){
        //System.out.println("연결 확인 : "+id);
        //try {
            memberService.modify(id, memberDto);
        //} catch (MemberNotFoundException e){
        //   return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       // }
        //return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    @Operation(
            summary = "회원 삭제",
            description = "사용자를 삭제 합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공(내용 없음)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "삭제 사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            )
    })
    public ResponseEntity<Void> deleteMember(@PathVariable("id") int id){
        //try {
            memberService.delMember( id );
        //} catch (MemberNotFoundException e) {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    @Operation(
            summary = "회원 추가",
            description = "사용자를 추가 합니다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "추가 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "추가성공")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복 id",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="중복된 id 입니다")
                    )
            )
    })
    public ResponseEntity<String> register(@ParameterObject @ModelAttribute MemberRegDto memberRegDto){
        /*
        try {
            Thread.sleep(1000);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
         */

        //try {
            memberService.insert(memberRegDto);
        //} catch (MemberDuplicateException e){
        //    return ResponseEntity.status(HttpStatus.CONFLICT).body("동일 id 존재");
       // }
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");

    }
}
