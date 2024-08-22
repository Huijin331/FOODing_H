package com.sw.fd.controller;

import com.sw.fd.entity.Alarm;
import com.sw.fd.entity.Group;
import com.sw.fd.entity.Invite;
import com.sw.fd.entity.Member;
import com.sw.fd.service.AlarmService;
import com.sw.fd.service.InviteService;
import com.sw.fd.service.MemberGroupService;
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
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private MemberGroupService memberGroupService;

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

                // 새로운 알림 엔티티 생성 및 설정
                Alarm alarm = new Alarm();
                alarm.setLinkedPk(String.valueOf(invite.getIno())); // 초대 엔티티의 ino 값을 문자열로 설정
                alarm.setAtype("모임장 수락 대기");
                // 알림을 받을 회원 (초대한 회원의 mno를 통해 찾아야 함)
                Member inviter = memberGroupService.findMemberByJno(invite.getLeadNum());
                alarm.setMember(inviter); // 초대한 회원을 알림의 대상자로 설정
                alarm.setIsChecked(0); // 알림 확인 여부를 미확인 상태로 설정 (0)

                // 알림 정보 저장
                alarmService.saveAlarm(alarm);

            } else if (invite.getItype() == 6) {
                invite.setItype(7); // itype을 7로 변경

                // 초대받은 회원과 모임 정보 가져오기
                Member invitedMember = invite.getMember();
                Group group = invite.getMemberGroup().getGroup();

                // 초대받은 회원을 모임에 일반회원으로 추가
                memberGroupService.addMemberToGroup(invitedMember, group, 0); // 0은 일반회원 권한
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

            // 알림 엔티티 생성 및 설정
            Alarm alarm = new Alarm();
            alarm.setLinkedPk(String.valueOf(invite.getIno())); // 초대 엔티티의 ino 값을 문자열로 설정
            alarm.setAtype("초대 거절");
            // 초대한 회원의 mno로 알림을 받을 회원 설정
            Member invitingMember = invite.getMemberGroup().getMember(); // 초대한 회원
            alarm.setMember(invitingMember); // 알림을 받을 회원
            alarm.setIsChecked(0); // 확인 여부는 0 (미확인 상태)

            // 알림 정보 저장
            alarmService.saveAlarm(alarm);
        }

        return "redirect:/inviteManage";
    }

    @PostMapping("/deleteInvite")
    public String deleteInvite(@RequestParam("inviteId") int inviteId) {
        inviteService.deleteInvite(inviteId);
        return "redirect:/inviteManage";
    }
}