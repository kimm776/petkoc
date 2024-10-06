package com.dog.petkoc.common.security;

import com.dog.petkoc.member.login.application.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails {

    private final Member member;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> userAttributes;

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(userAttributes);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <A> A getAttribute(String name) {
        return (A) userAttributes.get(name);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public String getProvider() {
        return member.getProvider();
    }
}
