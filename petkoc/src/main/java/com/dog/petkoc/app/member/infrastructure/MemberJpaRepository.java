package com.dog.petkoc.app.member.infrastructure;

import com.dog.petkoc.app.member.domain.entity.Member;
import com.dog.petkoc.app.member.domain.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, UUID>, MemberRepository {
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
