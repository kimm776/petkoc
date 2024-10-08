package com.dog.petkoc.member.login.infrastructure;

import com.dog.petkoc.member.login.application.entity.Member;
import com.dog.petkoc.member.login.domain.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, UUID>, MemberRepository {
    @Override
    default Member findMemberByEmail(String email) {
        return this.findByEmail(email);
    }
    @Override
    default boolean isExistingByEmail(String email) {
        return this.findByEmail(email) != null;
    }

    @Override
    default Member saveMember(Member member) {
        return save(member);
    }

    Member findByEmail(String email);
}
