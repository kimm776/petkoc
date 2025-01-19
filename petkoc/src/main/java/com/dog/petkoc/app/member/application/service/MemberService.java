package com.dog.petkoc.app.member.application.service;

import com.dog.petkoc.app.member.domain.entity.Member;
import com.dog.petkoc.app.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 등록
     * @param member
     */
    public void registerMember(Member member) {
        if (memberRepository.isExistingByEmail(member.getEmail())) {
            log.debug("이미 존재하는 이메일입니다...");
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (member.getPassword() != null){
            member.setPassword(passwordEncoder.encode(member.getPassword()));
        }
        memberRepository.saveMember(member);
    }
    public Member findByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.isExistingByEmail(email);
    }

}
