package com.sw.fd.repository;

import com.sw.fd.entity.Review;
import com.sw.fd.entity.ReviewTag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewTagRepository extends JpaRepository<ReviewTag, Integer> {
    List<ReviewTag> findByReview(Review review);

    @Modifying
    @Transactional
    @Query("DELETE FROM ReviewTag rt WHERE rt.review = :review")
    void deleteTags(@Param("review") Review review);

    //store 대표태그 기능을 위해 추가한 부분
    @EntityGraph(attributePaths = {"review.store", "tag"})
    List<ReviewTag> findByReview_Store_Sno(int sno);
}
