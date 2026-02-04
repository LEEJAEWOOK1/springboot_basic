package com.ex01.basic.repository;

import com.ex01.basic.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private List<MemberDto> DB;
    public MemberRepository(){
        //System.out.println("memberRepository 생성자");
        DB = new ArrayList<>();
        DB.add(new MemberDto(1,"aaa","aaaS","USER"));
        DB.add(new MemberDto(2,"bbb","bbbS","USER"));
        DB.add(new MemberDto(3,"ccc","cccS","USER"));
    }
    //목록 모두 찾기
    public List<MemberDto> findAll(){
        return DB;
    }
    public void repositoryTest(){
        System.out.println("repository 연결");
        //select * from members; List<MemberDto>
    }
    //id 찾기
    public Optional<MemberDto> findById(int id){
        //System.out.println("findByid : "+id);
        return DB.stream().filter(mem -> mem.getId() == id)
                .findFirst();
    } //DB 연동시 Optional로 돌려준다
    //username 찾기
    public Optional<MemberDto> findByUsername(String username){
        return DB.stream().filter(mem -> mem.getUsername().equals(username))
                .findFirst();
    }
    //id가 존재하는지 검사
    public boolean existById(int id){
        return DB.stream().filter(mem -> mem.getId() == id)
                .findFirst()
                .isPresent();
    }
    //저장(수정 시)
    public void save(int id, MemberDto memberDto){
        DB.set(id-1, memberDto); //index, 변경할 값
    }
    //삭제
    public boolean deleteById(int id){
        return DB.removeIf(m -> m.getId() == id);
    }
    //저장(추가 시)
    public void save(MemberDto memberDto){
        DB.add(memberDto);
    }
}
