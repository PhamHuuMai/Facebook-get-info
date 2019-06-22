package com.phm.spring.controller;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.User;
import facebook4j.auth.AccessToken;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.*;

import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;
import org.apache.tomcat.util.codec.binary.Base64;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("callback")
    @ResponseBody
    public String action(@RequestParam(name = "code", required = false) String code) {
        String aa = "";
        try {
            Facebook facebook = new FacebookFactory().getInstance();
            facebook.setOAuthAppId("415865425661793", "bb9136f15a8bdedd3b23c555dfff7d0e");
            facebook.setOAuthPermissions("email,publish_stream");
            facebook.setOAuthCallbackURL("");
            //facebook.setOAuthCallbackURL(_redirectURL);
            AccessToken token = facebook.getOAuthAccessToken(code);
            String accessToken = token.getToken();
            if (accessToken == null) {
                return null;
            }
            User user = facebook.getMe();
            String id = user.getId();
            String mail = user.getEmail();
            return "id = " + id + " = " + mail;
        } catch (Exception ex) {
            aa = ex.getLocalizedMessage();
        }
        return aa + " loi ";
    }

    private String getCode(String code1, String cliSec) throws Exception {
        Base64 base = new Base64(true);
        String[] signedRequest2 = code1.split("\\.", 2);
        String sig_2 = signedRequest2[1];
        String sig2 = new String(base.decodeBase64(sig_2.getBytes("UTF-8")));
        JsonParser parser = new JsonParser();
        JsonElement obj = parser.parse(sig2);
        JsonObject map = obj.getAsJsonObject();
        if (!(map.get("algorithm")).getAsString().equals("HMAC-SHA256")) {
            return "";
        }
        if (!getHmacSHA256(sig_2, cliSec).equals(signedRequest2[0])) {

            return "";
        }
        String code = map.get("code").getAsString();
        if (code == null) {
            return "";
        }
        return "";
    }

    private static String getHmacSHA256(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
        String hmacSt = new String(Base64.encodeBase64URLSafe(hmacData), "UTF-8");
        return hmacSt;
    }
}
