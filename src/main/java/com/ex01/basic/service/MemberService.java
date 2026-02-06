package com.ex01.basic.service;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.exception.InvalidLoginException;
import com.ex01.basic.exception.MemberDuplicateException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.MemberRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemRepository memRepository;

    public MemberService(){
        System.out.println("MemberService 생성자");
    }
    public void serviceTest(){
        System.out.println("서비스 test 연결 : "+memberRepository);
        //memberRepository2.repositoryTest();
    }
    // 전체 회원 보기
    public List<MemberDto> getList(int start){
        //List<MemberDto> list = memberRepository.findAll();
        int size = 3;
        Pageable pageable = PageRequest.of(start, size,
                Sort.by(Sort.Order.desc("id")));
        Page<MemberEntity> page = memRepository.findAll(pageable);

        if(page.isEmpty())
            throw new MemberNotFoundException("데이터 없다");

        return page.getContent().stream()
                .map(MemberDto::new)
                .toList();
        /*
        List<MemberDto> list = memRepository.findAll().stream()
                .map(memberEntity -> new MemberDto(memberEntity))
                .toList();
        if(list.isEmpty())
            throw new MemberNotFoundException("데이터 없다");
        return list;
         */

    }
    //특정 회원 보기
    public MemberDto getOne(int id){
        return memRepository.findById(id)
                .map(MemberDto::new)
                .orElseThrow(MemberNotFoundException::new);
        //System.out.println("service id : "+id);
        //Optional<MemberDto> opt = memberRepository.findById(id);
        //return opt.orElseThrow();
        /*
        return memberRepository.findById(id)
                //.orElseThrow(() -> new MemberNotFoundException());
                .orElseThrow(MemberNotFoundException::new);
         */
    }
    //회원 수정
    public void modify(int id, MemberDto memberDto){
        MemberEntity memberEntity = memRepository.findById(id)
                .orElseThrow(()->new MemberNotFoundException("수정 사용자 없음"));
        //BeanUtils.copyProperties(memberDto, memberEntity, "username");
        BeanUtils.copyProperties(memberDto, memberEntity);
        memRepository.save(memberEntity);
        //System.out.println("service id : "+id);
        /*
        boolean bool = memberRepository.existById(id);
        //System.out.println(bool);
        if(!bool)
            throw new MemberNotFoundException("수정 사용자 없음");
        memberRepository.save(id, memberDto);
         */
    }
    //회원 삭제
    public void delMember(int id ){
        /*
        boolean bool =  memberRepository.existById(id);
        if( !bool )
           throw new MemberNotFoundException("삭제 사용자 없음");
         */
        //boolean bool =  memberRepository.deleteById( id );
        if(!memRepository.existsById(id))
            throw new MemberNotFoundException("삭제 사용자 없음");
        memRepository.deleteById(id);
    }
    //회원 추가
    public void insert(MemberRegDto memberRegDto){
        //boolean bool = memberRepository.existById(memberDto.getId());
        boolean bool = memRepository.existsByUsername(memberRegDto.getUsername());
        if(bool)
            throw new MemberDuplicateException("중복 id");
        //memberRepository.save(memberDto);
        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memRepository.save(memberEntity);
    }
    //로그인
    public void login(LoginDto loginDto){
        //Optional<MemberDto> memberDto = memberRepository.findByUsername(loginDto.getUsername());
        Optional<MemberEntity> optional =
                memRepository.findByUsername(loginDto.getUsername());
        optional.ifPresentOrElse(
                mem -> {
                    if(!mem.getPassword().equals(loginDto.getPassword()))
                        throw new InvalidLoginException("비밀번호 틀림");
                },
                () -> {
                    throw new InvalidLoginException("사용자 id 없음");
                }
        );
    }
}
