package com.dog.petkoc.service;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일 회원 등록
     * @param email
     * @param password
     * @param fullName
     */
    public void registerMember(String email, String password, String fullName, String provider) {
        String encodedPassword = passwordEncoder.encode(password);
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encodedPassword);
        member.setFullName(fullName);
        member.setProvider(provider);
        memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("Member not found");
        }
        return new org.springframework.security.core.userdetails.User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }
}
