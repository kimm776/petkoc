package com.dog.petkoc.member.login.domain.repository;

import com.dog.petkoc.member.login.application.entity.Member;


public interface MemberRepository {
    Member findMemberByEmail(String email);
    boolean isExistingByEmail(String email);
    Member saveMember(Member member);
}
