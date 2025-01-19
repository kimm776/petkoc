package com.dog.petkoc.app.member.domain.repository;

import com.dog.petkoc.app.member.domain.entity.Member;


public interface MemberRepository {
    Member findMemberByEmail(String email);
    boolean isExistingByEmail(String email);
    Member saveMember(Member member);
}
