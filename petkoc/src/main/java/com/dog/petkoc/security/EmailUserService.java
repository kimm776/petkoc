package com.dog.petkoc.security;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EmailUserService implements UserDetailsService {
    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberService.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException("Member not found");
        }

        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return new UserPrincipal(member, authorities, new HashMap<>());
    }
}
