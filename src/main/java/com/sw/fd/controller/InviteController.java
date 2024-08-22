package com.sw.fd.controller;

import com.sw.fd.entity.Invite;
import com.sw.fd.entity.Member;
import com.sw.fd.service.InviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class InviteController {
    @Autowired
    private InviteService inviteService;

    @GetMapping("/inviteManage")
    public String inviteManage(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        // 로그인한 회원의 mno를 가져옵니다.
        int mno = member.getMno();

        // 사용자의 mno로 초대 목록을 조회합니다.
        List<Invite> invites = inviteService.getInvitesByMemberMno(mno);

        // 초대 목록을 모델에 추가합니다.
        model.addAttribute("invites", invites);

        return "inviteManage";
    }

    @PostMapping("/acceptInvite")
    public String acceptInvite(@RequestParam("inviteId") int inviteId, HttpSession session) {

        Invite invite = inviteService.findById(inviteId);
        if (invite != null) {
            if (invite.getItype() == 0) {
                invite.setItype(1); // itype을 1로 변경
            } else if (invite.getItype() == 6) {
                invite.setItype(7); // itype을 7로 변경
            }
            inviteService.saveInvite(invite); // 업데이트된 엔티티를 저장
        }

        return "redirect:/inviteManage";
    }

    @PostMapping("/rejectInvite")
    public String rejecttInvite(@RequestParam("inviteId") int inviteId, HttpSession session) {

        Invite invite = inviteService.findById(inviteId);
        if (invite != null) {
            if (invite.getItype() == 0) {
                invite.setItype(2); // itype을 2로 변경
            } else if (invite.getItype() == 6) {
                invite.setItype(8); // itype을 8로 변경
            }
            inviteService.saveInvite(invite); // 업데이트된 엔티티를 저장
        }

        return "redirect:/inviteManage";
    }

    @PostMapping("/deleteInvite")
    public String deleteInvite(@RequestParam("inviteId") int inviteId) {
        inviteService.deleteInvite(inviteId);
        return "redirect:/inviteManage";
    }
}