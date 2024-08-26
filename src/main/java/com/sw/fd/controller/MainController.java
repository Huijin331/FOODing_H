package com.sw.fd.controller;

import com.sw.fd.dto.MemberGroupDTO;
import com.sw.fd.entity.*;
import com.sw.fd.service.AlarmService;
import com.sw.fd.service.InviteService;
import com.sw.fd.service.MemberGroupService;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MainController {
    @Autowired
    private MemberGroupService memberGroupService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private InviteService inviteService;

    @GetMapping("/main")
    public String showMainPage(Model model, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        List<MemberGroupDTO> myMemberGroups = new ArrayList<>();
        Map<Integer, String> leaderList = new HashMap<>();
        Map<Integer, String> allMemberList = new HashMap<>();

        /* 알림 기능 추가 (희진) */
        if (loggedInMember != null) {
            boolean hasAlarms = alarmService.hasAlarms(loggedInMember);
/*            System.out.println("hasAlarms  = " + hasAlarms);*/
            model.addAttribute("hasAlarms", hasAlarms);

            if (hasAlarms) {
                List<Alarm> alarms = alarmService.getAlarmsByMember(loggedInMember.getMid());
                model.addAttribute("alarms", alarms);

                boolean alarmChecked = true;

                for (Alarm alarm : alarms) {
                    if (alarm.getIsChecked() == 0) {
                        alarmChecked = false;
                    }

                    // invite가 이미 지워져서 invite가 null이 될 경우 오류가 나서 추가(다혜)
                    String inviterName = "";
                    String groupName = "";
                    String inviteeName = "";

                    Invite invite = inviteService.getInviteByIno(Integer.parseInt(alarm.getLinkedPk()));
                    if (invite != null) {
                        inviterName = invite.getMemberGroup().getMember().getMnick();
                        groupName = invite.getMemberGroup().getGroup().getGname();
                        inviteeName = invite.getMember().getMnick();


                        if (alarm.getAtype().equals("일반 회원 초대") || alarm.getAtype().equals("모임장 초대")) {
                            alarm.setMessage(inviterName + "님이 " + groupName + " 모임에<br>회원님을 초대하였습니다.");
                        }
                        else if (alarm.getAtype().equals("초대 거절")) {
                            alarm.setMessage(inviteeName + "님이 초대를 거절하였습니다");
                        }
                        else if (alarm.getAtype().equals("모임장 수락 대기")) {
                            alarm.setMessage(inviteeName + "님이<br>모임장 수락을 요청하였습니다.");
                        }
                    }
                    else{
                        /*----------모임장 수락 이후에는 관련 알림이 삭제되므로 처리하지 않음(다혜)----------------*/

                    /*    else if (alarm.getAtype().equals("모임장 수락")) {
                        alarm.setMessage(inviteeName + "님의 초대를<br>모임장이 수락하였습니다.");
                        }*/
                        /*----------모임장 수락 승인을 위해 수락한 부분(다혜)----------------*/
                        if (alarm.getAtype().equals("모임장 수락 거절1")) {
                            alarm.setMessage("회원님이 받은 초대를 모임장이 수락 거절하였습니다.");
                        }
                        else if (alarm.getAtype().equals("모임장 수락 거절2")) {
                            alarm.setMessage("회원님의 초대를 모임장이 수락 거절하였습니다.");
                        }
                        else{
                            alarm.setMessage("이미 삭제된 초대에 대한 알림입니다.");
                        }
                    }

                }
                model.addAttribute("alarmChecked", alarmChecked);
            }
            else {
                model.addAttribute("hasAlarms", false);
            }
        }

        if (loggedInMember != null) {
            myMemberGroups = memberGroupService.getMemberGroupsWithGroup(loggedInMember);

            if (myMemberGroups.isEmpty())
                model.addAttribute("myMemberGroups", "모임방x");
            else {
                for (MemberGroupDTO memberGroup : myMemberGroups) {
/*                    System.out.println(memberGroup.getGroup().getGimage());*/
                    int thisGno = memberGroup.getGroup().getGno();
                    // 해당 gno 그룹의 모든 맴버 닉네임을 한줄의 String으로 만들어서 gno와 함께 Map화 (key= gno, value= 모임방의 모든 맴버 닉네임)
                    allMemberList.put(thisGno, memberGroupService.findMnicksByGroupGno(thisGno));
                    // 해당 gno 그룹의 모임장을 찾아서 모임장의 닉네임을 gno와 함께 Map화 (key= gno, value= 모임장 닉네임)
                    if (memberGroupService.getLeaderByGno(thisGno) != null) {
                        leaderList.put(thisGno, memberGroupService.getLeaderByGno(thisGno).getMember().getMnick());
                    }
                }
                model.addAttribute("myMemberGroups", myMemberGroups);
                model.addAttribute("leaderList", leaderList);
                model.addAttribute("allMemberList", allMemberList);
            }
        }

        List<Store> stores1 = storeService.getAllStores();
        List<Store> stores2 = storeService.getAllStores();
        stores1.sort(Comparator.comparingDouble(Store::getScoreArg).reversed());
        List<Store> rankedByScoreStores = stores1.subList(0, 5);
        stores2.sort(Comparator.comparingDouble(Store::getPickNum).reversed());
        List<Store> rankedByPickStores = stores2.subList(0, 5);

        model.addAttribute("rankByScore", rankedByScoreStores);
        model.addAttribute("rankByPick", rankedByPickStores);


        return "main";
    }
	
	// 확인 버튼 클릭 시 알림의 isChecked 상태를 1로 변경 (희진 추가)
    @PostMapping("/alarmChecked")
    public String alarmChecked(@RequestParam("alarmId") int alarmId, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember != null) {
            Alarm alarm = alarmService.findById(alarmId);
            if (alarm != null) {
                alarm.setIsChecked(1); // 확인된 상태로 설정
                alarmService.saveAlarm(alarm);
            }
        }
        return "redirect:/main";
    }
	
	
	/* 알림 기능 추가 (희진) */
    @PostMapping("/alarmDelete")
    public String alarmDelete(@RequestParam("alarmId") int alarmId, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember != null) {
            alarmService.deleteAlarm(alarmId); // 알림을 삭제하는 서비스 호출
        }
        return "redirect:/main";
    }
}
