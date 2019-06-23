package com.phm.spring.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phm.spring.FacebookAuthentication;
import com.phm.spring.FacebookUser;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("callback")
    @ResponseBody
    public String action(@RequestParam(name = "code", required = false) String code) {
        String aa = "";
        try {
            if (code != null) {
                FacebookUser user = FacebookAuthentication.newInstance(code)
                        .setAppId("415865425661793")
                        .setAppSecret("bb9136f15a8bdedd3b23c555dfff7d0e")
                        .getUserInfo();
                return "id = " + user.getId() + " = " + user.getName();
            }
        } catch (Exception ex) {
            aa = ex.getLocalizedMessage();
        }
        return aa + " loi ";
    }

}
