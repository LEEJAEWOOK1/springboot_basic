package com.ex01.basic.controller.post;

import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    //포스트 추가
    @PostMapping
    public ResponseEntity<String> insert(@ParameterObject @ModelAttribute PostDto postDto){
        postService.insert(postDto);
        return ResponseEntity.ok("데이터 추가");
    }
    //포스트 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<PostAllDto>> getPost(){
        return ResponseEntity.ok(postService.getPost());
    }
    @GetMapping("{id}")
    public ResponseEntity<PostDetailDto> getPostOne(
            @PathVariable("id") Long id,
            @RequestParam("number") Long number){
        return ResponseEntity.ok(postService.getPostOne(id, number));
    }
}
