package com.dog.petkoc.repository;

import com.dog.petkoc.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Member findByEmail(String email);
}
