package com.dog.petkoc.common.security;

import com.dog.petkoc.member.login.application.entity.Member;
import com.dog.petkoc.member.login.application.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserService extends DefaultOAuth2UserService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException("Member not found");
        }

        return this.extractUser(username);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        String provider = oAuth2UserRequest.getClientRegistration().getClientName();
        Map<String, Object> userInfo = super.loadUser(oAuth2UserRequest).getAttributes();

        try {
            Member member = this.extractMember(userInfo, provider);
            this.findOrCreateMember(member);
            return this.extractUser(member.getEmail());
        } catch (Exception e) {
            log.error("OAuth2UserService error: {}", e.getMessage(), e);
            throw new OAuth2AuthenticationException("OAuth2 user service error: " + e.getMessage());
        }
    }

    private Member extractMember(Map<String, Object> userInfo, String provider) {
        Member member = new Member();
        member.setProvider(provider);

        if (provider.equalsIgnoreCase("Naver")) {
            Map<String, Object> response = (Map<String, Object>) userInfo.get("response");
            this.validUserInfo(new String[] {"email", "name"}, response);
            member.setEmail((String) response.get("email"));
            member.setName((String) response.get("name"));

            return member;
        } else if (provider.equalsIgnoreCase("Kakao")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
            this.validUserInfo(new String[] {"email", "profile"}, kakaoAccount);
            member.setEmail((String) kakaoAccount.get("email"));

            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            this.validUserInfo(new String[] {"nickname"}, profile);
            member.setName((String) profile.get("nickname"));

            return member;
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }
    }


    private void findOrCreateMember(Member member) {
        if (memberService.existsByEmail(member.getEmail())) {
            log.debug("가입되어있음. 로그인처리로 넘어감.");
        } else {
            // FIXME: 바로 등록하지 않고 회원가입 페이지로 사용자 Email 넘기기
            memberService.registerMember(member);
        }
    }

    UserPrincipal extractUser(String email) {
        Member member = memberService.findByEmail(email);
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_MEMBER"));
        Map<String, Object> userAttriutes = new HashMap<>();
        userAttriutes.put("email", member.getEmail());
        userAttriutes.put("provider", member.getProvider());
        userAttriutes.put("name", member.getName());

        return new UserPrincipal(member, authorities, userAttriutes);
    }


    private void validUserInfo(String[] keys, Map<String, Object> userInfo) {
        for (String key : keys) {
            if (!userInfo.containsKey(key)) {
                throw new OAuth2AuthenticationException("서드파티 응답에 필수 항목이 존재하지 않습니다.");
            }
        }
    }

}
