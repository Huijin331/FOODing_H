package com.sw.fd.controller;

import com.sw.fd.dto.GroupDTO;
import com.sw.fd.entity.Group;
import com.sw.fd.entity.Member;
import com.sw.fd.entity.MemberGroup;
import com.sw.fd.service.GroupService;
import com.sw.fd.service.MemberGroupService;
import com.sw.fd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<GroupDTO> groups = groupService.getGroupsByMember(member);
        model.addAttribute("group", new GroupDTO());
        model.addAttribute("memberGroup", new MemberGroup());
        model.addAttribute("groups", groups);

        List<Integer> gnos = groups.stream().map(GroupDTO::getGno).collect(Collectors.toList());

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

        // 각 모임의 모든 회원 목록을 가져온다
        Map<Integer, List<MemberGroup>> groupMembersMap = leaderGroups.stream()
                .collect(Collectors.toMap(
                        Group::getGno,
                        group -> memberGroupService.findMembersByGroupGno(group.getGno())
                ));

        model.addAttribute("leaderGroups", leaderGroups);
        model.addAttribute("groupMembersMap", groupMembersMap);

        // 모임장인 경우 groupManage.jsp 페이지로 이동
        return "groupManage";
    }
}