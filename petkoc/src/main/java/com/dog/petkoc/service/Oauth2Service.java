package com.dog.petkoc.service;

import java.util.Map;

public interface Oauth2Service {
    public String getAuthorizeUri();
    public void getAccessToken(String code, String state);
    public void refreshAccessToken();
    public Map<String, String> getUserInfo(String accessToken);
}
