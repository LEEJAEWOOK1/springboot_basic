package com.ex01.basic.entity;

import com.ex01.basic.entity.post.PostEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="member_basic")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;
    private String fileName;

    @PrePersist
    public void prePersist(){
        if(this.role == null)
            this.role ="USER";
    }

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<PostEntity> posts = new ArrayList<>();
}
