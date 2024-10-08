package com.dog.petkoc.member.login.application.service;

import com.dog.petkoc.member.login.application.entity.Member;

public interface MemberService {

    void registerMember(Member member);
    Member findByEmail(String email);
    boolean existsByEmail(String email);

}
