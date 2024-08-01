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
        GroupDTO groupDTO = groupService.getGroupById(memberGroup.getGroup().getGno());
        Group group = new Group();
        group.setGno(groupDTO.getGno());
        group.setGname(groupDTO.getGname());

        memberGroupService.addMemberToGroup(newMember, group, 0);

        return "redirect:/groupList";
    }
}