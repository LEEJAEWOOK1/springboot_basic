package com.ex01.basic.dto.post;

import com.ex01.basic.entity.post.PostEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostAllDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;

    public PostAllDto(PostEntity postEntity){
        System.out.println(postEntity);
        BeanUtils.copyProperties(postEntity, this);
    }
}
