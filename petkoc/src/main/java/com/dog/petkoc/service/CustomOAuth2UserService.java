package com.dog.petkoc.service;

import com.dog.petkoc.entity.Member;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
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
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> userInfo;

        if (provider.equals("naver")) {
            userInfo = (Map<String, Object>) (super.loadUser(oAuth2UserRequest).getAttributes()).get("response");
        } else {
            userInfo = super.loadUser(oAuth2UserRequest).getAttributes();
        }

        validateAttributes(userInfo);

        // TODO: 회원가입 or 로그인 로직 처리

        Member member = new Member() {{
            setEmail((String) userInfo.get("email"));
            setFullName((String) userInfo.get("given_name"));
            setPassword((String) userInfo.get("id"));
            setProvider(provider);
        }};

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_MEMBER"));

        return new UserPrincipal(member, authorities, userInfo);
    }

    public void validateAttributes(Map<String, Object> attributes) {
        if (!attributes.containsKey("email") || !attributes.containsKey("name")) {
            throw new OAuth2AuthenticationException("서드파티 응답에 email 또는 name이 존재하지 않습니다.");
        }
    }
}
