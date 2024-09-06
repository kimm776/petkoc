package com.dog.petkoc.security;

import com.dog.petkoc.entity.Member;
import com.dog.petkoc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        String provider = oAuth2UserRequest.getClientRegistration().getClientName();
        Map<String, Object> userInfo;

        try {
            if (provider.equalsIgnoreCase("Naver")) {
                userInfo = (Map<String, Object>) (super.loadUser(oAuth2UserRequest).getAttributes()).get("response");
            } else {
                userInfo = super.loadUser(oAuth2UserRequest).getAttributes();
            }

            validateAttributes(userInfo);

            Member member = new Member();
            member.setEmail((String) userInfo.get("email"));
            member.setName((String) userInfo.get("name"));
            member.setPassword(null);
            member.setProvider(provider);

            if (memberService.existingByEmail(member.getEmail())) {
                checkExistingMember(member);
            } else {
                memberService.registerMember(member);
            }

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));
            return new UserPrincipal(member, authorities, userInfo);
        } catch (Exception e) {
            log.error("OAuth2UserService error: {}", e.getMessage());
            throw new OAuth2AuthenticationException("OAuth2 user service error");
        }
    }

    public void validateAttributes(Map<String, Object> attributes) {
        if (!attributes.containsKey("email") || !attributes.containsKey("name")) {
            throw new OAuth2AuthenticationException("서드파티 응답에 email 또는 name이 존재하지 않습니다.");
        }
    }

    public void checkExistingMember(Member member) {
        Member existingMember = memberService.findByEmail(member.getEmail());
        if (!existingMember.getProvider().equalsIgnoreCase(member.getProvider())) {
            log.error("이미 가입된 이메일입니다...");
            throw new OAuth2AuthenticationException("이미 가입된 이메일입니다. 이메일로 로그인해주세요.");
        }
    }

}
