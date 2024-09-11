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
        Map<String, Object> userInfo = super.loadUser(oAuth2UserRequest).getAttributes();

        try {
            Member member = validateAttributes(userInfo, provider);
            findOrCreateMember(member);
            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_MEMBER"));
            return new UserPrincipal(member, authorities, userInfo);
        } catch (Exception e) {
            log.error("OAuth2UserService error: {}", e.getMessage(), e);
            throw new OAuth2AuthenticationException("OAuth2 user service error: " + e.getMessage());
        }
    }

    private Member validateAttributes(Map<String, Object> userInfo, String provider) {
        if (provider.equalsIgnoreCase("Naver")) {
            return validateNaverAttributes(userInfo);
        } else if (provider.equalsIgnoreCase("Kakao")) {
            return validateKakaoAttributes(userInfo);
        } else {
            throw new OAuth2AuthenticationException("Unsupported provider: " + provider);
        }
    }

    private Member validateNaverAttributes(Map<String, Object> userInfo) {
        userInfo = (Map<String, Object>) userInfo.get("response");
        if (!userInfo.containsKey("email") || !userInfo.containsKey("name")) {
            throw new OAuth2AuthenticationException("Naver 응답에 필수 속성이 존재하지 않습니다.");
        }
        Member member = new Member();
        member.setProvider("Naver");
        member.setEmail((String) userInfo.get("email"));
        member.setName((String) userInfo.get("name"));
        return member;
    }

    private Member validateKakaoAttributes(Map<String, Object> userInfo) {
        userInfo = (Map<String, Object>) userInfo.get("kakao_account");
        if (!userInfo.containsKey("email") || !userInfo.containsKey("profile")) {
            throw new OAuth2AuthenticationException("Kakao 응답에 필수 속성이 존재하지 않습니다.");
        }
        Member member = new Member();
        member.setProvider("Kakao");
        member.setEmail((String) userInfo.get("email"));
        Map<String, Object> profile = (Map<String, Object>) userInfo.get("profile");
        member.setName((String) profile.get("nickname"));
        return member;
    }

    private void findOrCreateMember(Member member) {
        Member existingMember = memberService.findByEmail(member.getEmail());
        if (existingMember != null) {
            if (!existingMember.getProvider().equalsIgnoreCase(member.getProvider())) {
                log.error("이미 가입된 이메일입니다...");
                throw new OAuth2AuthenticationException("이미 가입된 이메일입니다. 이메일로 로그인해주세요.");
            }
        } else {
            memberService.registerMember(member);
        }
    }

}
