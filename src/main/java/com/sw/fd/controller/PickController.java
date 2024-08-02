package com.sw.fd.controller;

import com.sw.fd.entity.Member;
import com.sw.fd.service.PickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class PickController {

    @Autowired
    private PickService pickService;

    @PostMapping("/pick")
    @ResponseBody
    public String pickStore(@RequestParam("sno") int sno, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "error"; // 로그인되지 않은 상태에서 예외 처리
        }

        int mno = loggedInMember.getMno();
        boolean isPicked = pickService.togglePick(mno, sno);
        return isPicked ? "picked" : "unpicked";
    }

    @PostMapping("/checkPick")
    @ResponseBody
    public String checkPick(@RequestParam("sno") int sno, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "unpicked"; // 로그인되지 않은 상태에서 예외 처리
        }

        int mno = loggedInMember.getMno();
        boolean isPicked = pickService.isPicked(mno, sno);
        return isPicked ? "picked" : "unpicked";
    }
}
