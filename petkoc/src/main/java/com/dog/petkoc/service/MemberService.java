package com.dog.petkoc.service;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.repository.MemberRepository;
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
     * 이메일 회원 등록
     * @param member
     */
    public void registerMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            log.debug("이미 존재하는 이메일입니다...");
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        if (member.getPassword() != null){
            member.setPassword(passwordEncoder.encode(member.getPassword()));
        }
        memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public boolean existingByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

}
