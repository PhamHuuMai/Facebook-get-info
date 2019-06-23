package com.phm.spring;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.vn.hitex.util.HttpUtil;

public class FacebookAuthentication {

    private String appId;
    private String appSecret;
    private String code;
    private String accessToken;

    private static final String REDIRECT_URL = "";
    private static final String GET_ACCESS_TOKEN_URL_FORMAT = "https://graph.facebook.com/v3.3/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s";
    private static final String GET_USER_INFO_URL_FORMAT = "https://graph.facebook.com/me?fields=%s&access_token=%s";

    private FacebookAuthentication(String code) {
        this.code = code;
    }

    public static FacebookAuthentication newInstance(String code) {
        return new FacebookAuthentication(code);
    }

    public FacebookAuthentication setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    public FacebookAuthentication setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }

    public FacebookUser getUserInfo() throws ParseException, Exception {
        accessToken = getAccessToken();
        if (accessToken == null) {
            throw new Exception();
        }
        return getUser();
    }

    private String getAccessToken() throws ParseException {
        String url = String.format(GET_ACCESS_TOKEN_URL_FORMAT, appId, REDIRECT_URL, appSecret,code);
        String result = HttpUtil.sendHttpGet(url);
        if (result != null) {
            JSONParser jSONParser = new JSONParser();
            JSONObject json = (JSONObject) jSONParser.parse(result);
            String accessToken = (String) json.getOrDefault("access_token", "");
            return accessToken;
        }
        return null;
    }

    private FacebookUser getUser() throws ParseException, Exception {
        FacebookUser facebookUser = new FacebookUser();
        String url = String.format(GET_USER_INFO_URL_FORMAT, "email,first_name,last_name,middle_name,name,picture",
                accessToken);
        String result = HttpUtil.sendHttpGet(url);
        if (result != null) {
            JSONParser jSONParser = new JSONParser();
            JSONObject json = (JSONObject) jSONParser.parse(result);
            String name = (String) json.getOrDefault("name", "");
            facebookUser.setName(name);
            String email = (String) json.getOrDefault("email", "");
            facebookUser.setEmail(email);
            String picture = getPicUrl((JSONObject) json.getOrDefault("picture", ""));
            facebookUser.setPictureUrl(picture);
            String id = (String) json.getOrDefault("id", "");
            facebookUser.setId(id);
        }else{
            throw new Exception();   
        }
        return facebookUser;
    }

    private String getFieldStr(String... fields) {
        StringBuilder field = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            field.append(fields[i]);
            if (i != (fields.length - 1)) {
                field.append(",");
            }
        }
        return field.toString();
    }

    private String getPicUrl(JSONObject data) {
        if (data != null) {
            return (String) data.getOrDefault("url", "");
        }
        return "";
    }

}
