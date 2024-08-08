package com.sw.fd.repository;

import com.sw.fd.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByTno(int tno);
    @Query("SELECT t FROM Tag t JOIN t.reviewTags rt WHERE rt.review.rno = :rno")
    List<Tag> findTagsByRno(@Param("rno") int rno);
}
