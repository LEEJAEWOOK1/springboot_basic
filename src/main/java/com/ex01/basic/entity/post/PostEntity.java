package com.ex01.basic.entity.post;

import com.ex01.basic.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="basic_post")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="number", nullable = true)
    private MemberEntity memberEntity;

    private String title;
    private String content;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updateTime;
    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate(){
        this.updateTime = LocalDateTime.now();
    }
}
