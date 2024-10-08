package com.dog.petkoc.member.login.application.service.Impl;

import com.dog.petkoc.member.login.application.entity.Member;
import com.dog.petkoc.member.login.application.service.MemberService;
import com.dog.petkoc.member.login.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 등록
     * @param member
     */
    @Override
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
    @Override
    public Member findByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberRepository.isExistingByEmail(email);
    }

}
