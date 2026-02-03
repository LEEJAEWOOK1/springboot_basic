package com.ex01.basic.service;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberDuplicateException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public MemberService(){
        System.out.println("MemberService 생성자");
    }
    public void serviceTest(){
        System.out.println("서비스 test 연결 : "+memberRepository);
        memberRepository.repositoryTest();
    }
    public List<MemberDto> getList(){
        List<MemberDto> list = memberRepository.findAll();
        if(list.isEmpty())
            throw new MemberNotFoundException("데이터 없다");
        return list;
    }
    public MemberDto getOne(int id){
        //System.out.println("service id : "+id);
        //Optional<MemberDto> opt = memberRepository.findById(id);
        //return opt.orElseThrow();
        return memberRepository.findById(id)
                //.orElseThrow(() -> new MemberNotFoundException());
                .orElseThrow(MemberNotFoundException::new);
    }
    public void modify(int id, MemberDto memberDto){
        //System.out.println("service id : "+id);
        boolean bool = memberRepository.existById(id);
        //System.out.println(bool);
        if(!bool)
            throw new MemberNotFoundException("수정 사용자 없음");
        memberRepository.save(id, memberDto);
    }
    public void delMember(int id ){
        /*
        boolean bool =  memberRepository.existById(id);
        if( !bool )
           throw new MemberNotFoundException("삭제 사용자 없음");
         */
        boolean bool =  memberRepository.deleteById( id );
        if( !bool )
            throw new MemberNotFoundException("삭제 사용자 없음");
    }
    public void insert(MemberDto memberDto){
        boolean bool = memberRepository.deleteById(memberDto.getId());
        if(bool)
            throw new MemberDuplicateException("중복 id");
        memberRepository.save(memberDto);
    }

}
