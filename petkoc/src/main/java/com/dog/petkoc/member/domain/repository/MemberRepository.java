package com.dog.petkoc.member.domain.repository;

import com.dog.petkoc.member.application.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Member findByEmail(String email);
    boolean existsByEmail(String email);
}
