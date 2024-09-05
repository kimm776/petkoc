package com.dog.petkoc.util;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SessionUtil {

    private final HttpSession session;

    /**
     * 세션에 값을 저장하는 메서드
     * @param key 세션에 저장할 키
     * @param value 세션에 저장할 값
     */
    public void setAttribute(String key, Object value) {
        session.setAttribute(key, value);
    }

    /**
     * 세션에서 값을 가져오는 메서드
     * @param key 세션에서 가져올 키
     * @param <T> 반환 타입
     * @return 세션에 저장된 값, 없으면 null
     */
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) session.getAttribute(key);
    }

    /**
     * 세션에서 값을 삭제하는 메서드
     * @param key 삭제할 키
     */
    public void removeAttribute(String key) {
        session.removeAttribute(key);
    }

    /**
     * 세션을 무효화하는 메서드
     */
    public void invalidate() {
        session.invalidate();
    }

     public String generateState() {
        // UUID를 사용하여 state 값 생성
         String stateString = UUID.randomUUID().toString();
         setAttribute("state", stateString);
        return stateString;
    }
}
