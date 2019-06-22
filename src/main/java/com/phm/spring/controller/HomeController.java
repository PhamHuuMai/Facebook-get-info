package com.phm.spring.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("callback")
    @ResponseBody
    public String action(@RequestParam("ow") String ow,
            @RequestParam("error") String error,
            @RequestParam("sr") String sr,
            HttpServletRequest request) {
        String owAtt = (String) request.getAttribute("ow");
        String errorAtt = (String) request.getAttribute("error");
        String srAtt = (String) request.getAttribute("sr");

        return "ow = " + ow
                + "error = " + error
                + "sr = " + sr
                + "ow att = " + owAtt
                + "error att = " + errorAtt
                + "sr att = " + srAtt;
    }

}
