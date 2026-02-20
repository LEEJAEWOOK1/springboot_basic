package com.ex01.basic.service.post;

import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.exception.post.MemberNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemRepository memRepository;
    //post 추가
    public void insert(PostDto postDto){
        MemberEntity memberEntity = memRepository.findById(postDto.getNumber())
                .orElseThrow(() -> new MemberNotFoundException("회원 가입 먼저 하세요"));

        PostEntity postEntity = new PostEntity();
        BeanUtils.copyProperties(postDto, postEntity);
        postEntity.setMemberEntity(memberEntity);

        postRepository.save(postEntity);
    }
    //전체 post 조회
    public List<PostAllDto> getPost(){
        return postRepository.findAll().stream()
                .map(PostAllDto::new)
                .toList();
    }
    public PostDetailDto getPostOne(Long id, Long number){
        PostDetailDto postDetailDto = postRepository.findById(id)
                .map(PostDetailDto::new)
                .orElseThrow(
                        () -> new MemberNotFoundException("존재하지 않는 글")
                );
        return postDetailDto;
    }
}
