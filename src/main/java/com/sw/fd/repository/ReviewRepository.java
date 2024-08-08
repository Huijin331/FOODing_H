package com.sw.fd.repository;

import com.sw.fd.entity.Review;
import com.sw.fd.entity.Store;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Optional<Review> findByRno(int rno);
    List<Review> findByStore_Sno(int sno); // 수정된 부분
    List<Review> findByMember_Mno(int mno);

    //별점 평균을 구하기 위해서 추가한 부분 (다혜)
    @Query("SELECT AVG(r.rstar) FROM Review r WHERE r.store.sno = :sno")
    Double findAverageScoreBySno(@Param("sno") int sno);
}