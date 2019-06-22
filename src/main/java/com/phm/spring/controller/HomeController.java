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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
            AccessToken token = facebook.getOAuthAccessToken(getAccessToken(code));
            String accessToken = token.getToken();
            if (accessToken == null) {
                return null;
            }
            User user = facebook.getMe();
            String id = user.getId();
            String mail = user.getEmail();
            return "id = " + id + " code = " + mail;
        } catch (Exception ex) {
            aa = ex.getLocalizedMessage();
        }
        return aa + " loi ";
    }

    private String getAccessToken(String code) {

        try {
            String url = "https://graph.facebook.com/v3.3/oauth/access_token?client_id=415865425661793&redirect_uri=https://maiph.herokuapp.com/callback&client_secret=bb9136f15a8bdedd3b23c555dfff7d0e&code=";
            url = url + code;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
//            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            System.out.println(response.toString());

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(response.toString());
            String a = (String)json.get("access_token");
            return a;
        } catch (Exception ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
