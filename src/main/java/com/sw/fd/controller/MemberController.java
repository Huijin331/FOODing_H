package com.sw.fd.controller;

import com.sw.fd.entity.Member;
import com.sw.fd.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/registerSelect")
    public String selectRegister() {
        return "registerSelect";
    }

    @GetMapping("/register/user")
    public String showUserForm(Model model) {
        Member member = new Member();
        member.setMtype(0); // 손님 (일반회원)으로 설정
        model.addAttribute("member", member);
        model.addAttribute("memberType", "손님");
        return "registerUser";
    }

    @GetMapping("/register/owner")
    public String showOwnerForm(Model model) {
        Member member = new Member();
        member.setMtype(1); // 사장님으로 설정
        model.addAttribute("member", member);
        model.addAttribute("memberType", "사장님");
        return "registerOwner";
    }

    @PostMapping("/register/user")
    public String registerUser(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("memberType", "손님");
            return "registerUser";
        }

        // 아이디 중복 체크
        if (memberService.isMidExists(member.getMid())) {
            bindingResult.rejectValue("mid", "error.member", "이미 사용 중인 아이디입니다.");
            model.addAttribute("memberType", "손님");
            return "registerUser";
        }

        // 닉네임 중복 체크
        if (memberService.isMnickExists(member.getMnick())) {
            bindingResult.rejectValue("mnick", "error.member", "이미 사용 중인 닉네임입니다.");
            model.addAttribute("memberType", "손님");
            return "registerUser";
        }

        memberService.saveMember(member);
        model.addAttribute("message", "일반 회원 가입 성공! 환영합니다!");
        return "login";
    }

    @PostMapping("/register/owner")
    public String registerOwner(@Valid @ModelAttribute("member") Member member, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("memberType", "사장님");
            return "registerOwner";
        }

        // 아이디 중복 체크
        if (memberService.isMidExists(member.getMid())) {
            bindingResult.rejectValue("mid", "error.member", "이미 사용 중인 아이디입니다.");
            model.addAttribute("memberType", "사장님");
            return "registerUser";
        }

        // 닉네임 중복 체크
        if (memberService.isMnickExists(member.getMnick())) {
            bindingResult.rejectValue("mnick", "error.member", "이미 사용 중인 닉네임입니다.");
            model.addAttribute("memberType", "사장님");
            return "registerUser";
        }

        memberService.saveMember(member);
        model.addAttribute("message", "사장님 회원 가입 성공! 환영합니다!");
        return "login";
    }

    // 회원 정보 조회
    @GetMapping("/member/view")
    public String viewMember(@RequestParam("mid") String mid, Model model) {
        Member member = memberService.findMemberById(mid);
        if (member == null) {
            model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
            return "error";
        } else {
            model.addAttribute("member", member);
            return "viewMember";
        }
    }

    // 회원 정보 수정 폼 보여주기
    @GetMapping("/member/edit/{mid}")
    public String showEditForm(@PathVariable("mid") String mid, Model model) {
        Member member = memberService.findMemberById(mid);
        if (member == null) {
            return "redirect:/member/view";

        } else {
            member.setMpass("");
            model.addAttribute("member", member);
            return "editMember"; // 수정 폼으로 이동
        }
    }

    @PostMapping("/member/edit")
    public String updateMember(@ModelAttribute("member") @Valid Member updatedMember,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "editMember"; // 입력 폼으로 다시 이동
        }

        Member existingMemberOpt = memberService.findMemberById(updatedMember.getMid());
        if (existingMemberOpt == null) {
            model.addAttribute("error", "회원 정보를 찾을 수 없습니다.");
            return "error";
        } else {
            Member existingMember = existingMemberOpt;

            // 기존 정보를 새로 입력된 정보로 업데이트
            existingMember.setMpass(updatedMember.getMpass());
            existingMember.setMnick(updatedMember.getMnick());
            existingMember.setMbirth(updatedMember.getMbirth());
            existingMember.setMphone(updatedMember.getMphone());
            existingMember.setMemail(updatedMember.getMemail());
            existingMember.setMaddr(updatedMember.getMaddr());

            memberService.updateMember(existingMember); // 회원 정보 업데이트
            model.addAttribute("message", "회원 정보가 성공적으로 수정되었습니다.");
            return "redirect:/member/view?mid=" + updatedMember.getMid();
        }
    }

    @GetMapping("/myPage")
    public String showMyPage(HttpSession session, Model model) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            // 로그인되지 않은 상태에서 접근 시 예외 처리 또는 로그인 페이지로 리다이렉트
            return "redirect:/login"; // 예시로 로그인 페이지로 리다이렉트 설정
        }
        model.addAttribute("loggedInMember", loggedInMember);
        return "myPage"; // 마이페이지 JSP 파일명
    }
}
