package com.sw.fd.service;

import com.sw.fd.entity.Review;
import com.sw.fd.repository.ReviewRepository;
import com.sw.fd.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private StoreRepository storeRepository;

    public Review saveReview(Review review) {
        review.setRdate(LocalDate.now());
        return reviewRepository.save(review);
    }

    public Review getReviewByRno(int rno) {
        return (Review) reviewRepository.findByRno(rno).orElse(null);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsBySno(int sno) {
        return reviewRepository.findByStore_Sno(sno);
    }

    @Transactional
    public List<Review> getReviewsByMno(int mno) {
        return reviewRepository.findByMember_Mno(mno);
    }
}
