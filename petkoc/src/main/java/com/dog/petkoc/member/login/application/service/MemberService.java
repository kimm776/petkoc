package com.dog.petkoc.member.login.application.service;

import com.dog.petkoc.member.login.application.entity.Member;
import com.dog.petkoc.member.login.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

public interface MemberService {

    void registerMember(Member member);
    Member findByEmail(String email);
    boolean existingByEmail(String email);

}
