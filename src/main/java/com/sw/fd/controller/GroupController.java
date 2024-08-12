package com.sw.fd.controller;

import com.sw.fd.dto.GroupDTO;
import com.sw.fd.dto.MemberGroupDTO;
import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.MemberGroup;
import com.sw.fd.service.GroupService;
import com.sw.fd.service.MemberGroupService;
import com.sw.fd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberGroupService memberGroupService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/groupList")
    public String groupList(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }
        String nick = member.getMnick();
        System.out.println("Member의 이름: " + nick);

        List<MemberGroupDTO> memberGroups = memberGroupService.getMemberGroupsWithGroup(member);
        for (MemberGroupDTO memberGroup : memberGroups) {
            System.out.println(memberGroup.getJno() + "의 getGroup().getGname() = :" + memberGroup.getGroup().getGname());
        }

        model.addAttribute("group", new GroupDTO());
        model.addAttribute("memberGroup", new MemberGroup());
        model.addAttribute("memberGroups", memberGroups);

        List<Integer> gnos = new ArrayList<>();
        for (MemberGroupDTO memberGroup : memberGroups) {
            gnos.add(memberGroup.getGroup().getGno());
        }

        List<MemberGroup> allMembers = memberService.getMemberGroupsByGnos(gnos);

        List<MemberGroup> leaderList = new ArrayList<>();
        for (MemberGroup memberGroup : allMembers) {
            if (memberGroup.getJauth() == 1) {
                leaderList.add(memberGroup);
            }
        }
        model.addAttribute("allMembers", allMembers);
        model.addAttribute("leaderList", leaderList);

        return "groupList";
    }

    @PostMapping("/groupList")
    public String groupListSubmit(@ModelAttribute GroupDTO groupDTO, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }
        groupService.createGroup(groupDTO);
        GroupDTO createdGroupDTO = groupService.getGroupById(groupDTO.getGno());
        Group group = new Group();

        group.setGno(createdGroupDTO.getGno());
        group.setGname(createdGroupDTO.getGname());
        memberGroupService.addMemberToGroup(member, group, 1);

        return "redirect:/groupList";
    }

    @PostMapping("/addMember")
    public String addMemberSubmit(@ModelAttribute MemberGroup memberGroup, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        Member newMember = memberService.getMemberById(memberGroup.getMember().getMid());
        if (newMember == null) {
            model.addAttribute("error", "해당 ID의 회원은 존재하지 않습니다.");
            return groupList(model, session);
        }

        GroupDTO groupDTO = groupService.getGroupById(memberGroup.getGroup().getGno());
        Group group = new Group();
        group.setGno(groupDTO.getGno());
        group.setGname(groupDTO.getGname());

        // 추가하려는 회원이 이미 모임에 존재하는지 확인
        if (memberGroupService.isMemberInGroup(newMember.getMid(), group.getGno())) {
            model.addAttribute("error", "이미 모임에 참여하고 있는 회원입니다.");
            return groupList(model, session);
        }

        memberGroupService.addMemberToGroup(newMember, group, 0);

        return "redirect:/groupList";
    }

    @GetMapping("/groupManage")
    public String groupManage(Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        // 회원이 모임장인 모임이 있는지 확인
        List<Group> leaderGroups = memberGroupService.findGroupsWhereMemberIsLeader(member.getMid());
        if (leaderGroups.isEmpty()) {
            // 모임장이 아닌 경우, 오류 메시지와 함께 메인 화면으로 이동
            model.addAttribute("error", "모임장 권한이 없으므로 메인 화면으로 이동합니다.");
            return "main";
        }

        List<MemberGroup> allMemberGroups = new ArrayList<>();
        for (Group group : leaderGroups) {
            List<MemberGroup> memberGroupsForGroup = memberGroupService.findMembersByGroupGno(group.getGno());
            allMemberGroups.addAll(memberGroupsForGroup);
        }

        model.addAttribute("leaderGroups", leaderGroups);
        model.addAttribute("allMemberGroups", allMemberGroups);

        // 모임장인 경우 groupManage.jsp 페이지로 이동
        return "groupManage";
    }

    @PostMapping("/updateGroupName")
    public String updateGroupName(HttpSession session, Model model, String newGname, int gno) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }
        System.out.println("newGname = " + newGname);
        // GroupDTO에서 gno를 통해 그룹 엔티티를 조회
        GroupDTO gDTO = groupService.getGroupById(gno);
        if (gDTO != null) {
            String originalGname = gDTO.getGname();
            System.out.println("originalGname = " + originalGname);

            // 새로운 이름이 원래의 이름과 같은지 비교
            if (newGname.equals(originalGname)) {
                model.addAttribute("error", "입력한 모임명이 기존 모임명과 동일합니다.");
                return groupManage(model, session); // 수정 페이지로 이동
            }

            // GroupDTO의 newGname으로 그룹명 업데이트
            Group group = new Group();
            group.setGno(gDTO.getGno());
            group.setGname(newGname);
            group.setGdate(gDTO.getGdate());
            // 그룹 엔티티를 저장하여 업데이트 수행
            groupService.save(group);
        }

        return "redirect:/groupManage";
    }

    @PostMapping("/deleteMemberToGroup")
    public String deleteMemberToGroup(@ModelAttribute("gno") int gno,
                               @ModelAttribute("mid") String memberId,
                               HttpSession session,
                               Model model) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        // 모임장만 특정 그룹에서 회원을 삭제할 수 있습니다.
        if (!memberGroupService.isMemberInGroup(memberId, gno)) {
            model.addAttribute("errorMessage", "입력한 회원은 해당 모임에 없습니다.");
            return groupManage(model, session);
        }

        MemberGroup deleteMg = memberGroupService.getMemberGroupByGroupGnoAndMemberMid(gno, memberId);
        memberGroupService.removeMemberGroup(deleteMg);

        return "redirect:/groupManage";
    }

    @PostMapping("/addMemberToGroup")
    public String addMemberToGroup(int gno,
                                   String mid,
                                   HttpSession session,
                                   Model model) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        // 입력된 회원 ID와 그룹 번호를 이용해 추가하려는 회원과 그룹을 조회
        Member newMember = memberService.getMemberById(mid);
        if (newMember == null) {
            model.addAttribute("errorMessage2", "해당 ID의 회원은 존재하지 않습니다.");
            return groupManage(model, session);
        }

        // 추가하려는 회원이 이미 모임에 존재하는지 확인
        if (memberGroupService.isMemberInGroup(mid, gno)) {
            model.addAttribute("errorMessage2", "이미 모임에 참여하고 있는 회원입니다.");
            return groupManage(model, session);
        }

        // GroupDTO를 통해 그룹 정보를 가져오고, Group 객체를 생성
        GroupDTO groupDTO = groupService.getGroupById(gno);
        Group group = new Group();
        group.setGno(groupDTO.getGno());
        group.setGname(groupDTO.getGname());

        // 회원을 그룹에 추가
        memberGroupService.addMemberToGroup(newMember, group, 0);

        return "redirect:/groupManage";
    }

    @PostMapping("/leaveGroup")
    public String leaveGroup(int gno, HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        System.out.println("Attempting to leave group: " + gno);
        System.out.println("Member ID: " + member.getMid());

        MemberGroup memberGroup = memberGroupService.getMemberGroupByGroupGnoAndMemberMid(gno, member.getMid());
        if (memberGroup != null && memberGroup.getJauth() == 0) {
            memberGroupService.removeMemberGroup(memberGroup);
        }

        return "redirect:/groupList";
    }

    @GetMapping("/transferJauth")
    public String showTransferJauth(@RequestParam("gno") int gno, Model model, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            return "redirect:/login";
        }

        // gno와 현재 로그인된 멤버의 ID를 사용하여 해당 MemberGroup 객체를 가져옴
        MemberGroup memberGroup = memberGroupService.getMemberGroupByGroupGnoAndMemberMid(gno, member.getMid());
        if (memberGroup == null) {
            model.addAttribute("error", "해당 그룹을 찾을 수 없습니다.");
            return "redirect:/groupList";
        }

        // 현재 모임장의 ID를 제외한 일반 회원 목록을 가져옴
        List<MemberGroupDTO> memberGroups = memberGroupService.findMembersByGroupGnoWithDTO(memberGroup.getGroup().getGno());
        List<MemberGroupDTO> regularMembers = new ArrayList<>();
        for (MemberGroupDTO mg : memberGroups) {
            if (mg.getJauth() == 0 ) {
                regularMembers.add(mg);
                System.out.println(mg.getMemberNick());
            }
        }

        model.addAttribute("memberGroup", memberGroup);
        model.addAttribute("regularMembers", regularMembers);

        return "transferJauth";
    }

    @PostMapping("/transferJauth")
    @ResponseBody
    public Map<String, String> transferJauth(@RequestParam("gno") int gno,
                                @RequestParam("memberNick") String newLeaderNick,
                                HttpSession session, Model model) {
        Map<String, String> response = new HashMap<>();
        Member member = (Member) session.getAttribute("loggedInMember");
        if (member == null) {
            response.put("status", "error");
            response.put("message", "로그인된 회원 정보가 없습니다.");
            return response;
        }

        // 현재 모임장 확인
        MemberGroup currentLeaderGroup = memberGroupService.getMemberGroupByGroupGnoAndMemberMid(gno, member.getMid());

        // 새로운 모임장으로 설정할 회원 찾기
        MemberGroup newLeaderGroup = memberGroupService.findMemberGroupByGroupGnoAndNick(gno, newLeaderNick);

        // 권한 위임 및 기존 모임장 권한 변경
        memberGroupService.updateMemberGroupJauth(gno, newLeaderGroup.getMember().getMid(), 1);
        memberGroupService.updateMemberGroupJauth(gno, member.getMid(), 0);

        // 기존 모임장 그룹에서 제거
        memberGroupService.removeMemberGroup(currentLeaderGroup);

        response.put("status", "success");
        response.put("message", "모임장 권한 위임이 성공했습니다.");
        return response;
    }
}