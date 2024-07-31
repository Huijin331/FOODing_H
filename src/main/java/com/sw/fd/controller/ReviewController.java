package com.sw.fd.controller;

import com.sw.fd.entity.Member;
import com.sw.fd.entity.Review;
import com.sw.fd.entity.Store;
import com.sw.fd.service.MemberService;
import com.sw.fd.service.ReviewService;
import com.sw.fd.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/review")
    public String review(@RequestParam("sno") int sno, Model model) {

        List<Review> reviews = reviewService.getReviewsBySno(sno);
        Store store = storeService.getStoreById(sno);
        model.addAttribute("reviews", reviews);
        model.addAttribute("review", new Review()); // 모델에 빈 Review 객체 추가
        model.addAttribute("sno", sno); // sno도 모델에 추가
        model.addAttribute("store", store); // 가게 정보도 모델에 추가
        model.addAttribute("isEmpty", reviews.isEmpty());
        return "review";
    }

    @PostMapping("/review")
    public String addReview(@ModelAttribute Review review, @RequestParam("sno") int sno, HttpSession session) {
        Member member = (Member) session.getAttribute("loggedInMember");

        if (member == null) {
            // 회원 정보가 없으면 에러 처리
            return "error"; // 적절한 에러 페이지로 리다이렉션
        }

        // sno를 이용하여 Store 객체를 가져오기
        Store store = storeService.getStoreById(sno);
        if (store == null) {
            // Store 객체가 없으면 에러 처리
            return "error"; // 적절한 에러 페이지로 리다이렉션
        }

        // 설정자 사용하여 필요한 필드 설정
        review.setMember(member);
        review.setStore(store); // Store 객체 설정

        // 리뷰를 DB에 저장
        reviewService.saveReview(review);

        // 리뷰 저장 후 해당 가게의 리뷰 페이지로 리다이렉션
        return "redirect:/storeDetail?sno=" + sno; // 여기가 storeDetail로 가야함
    }

    @GetMapping("/myReviews")
    public String showMyReviews(Model model, HttpSession session) {
        Member loggedInMember = (Member) session.getAttribute("loggedInMember");

        if (loggedInMember == null) {
            // 로그인되지 않은 상태에서 접근 시 예외 처리 또는 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 로그인한 회원의 mno를 가져와서 해당 회원이 작성한 리뷰들을 가져옴
        int mno = loggedInMember.getMno();
        List<Review> reviews = reviewService.getReviewsByMno(mno);
        model.addAttribute("reviews", reviews);

        return "myReviews"; // 내가 쓴 리뷰 목록을 보여주는 JSP 파일명
    }
}