package com.sw.fd.repository;

import com.sw.fd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMidAndMpass(String mid, String mpass);

    Optional<Member> findByMno(int mno);
    Optional<Member> findByMid(String mid);

    boolean existsByMid(String mid);
    boolean existsByMnick(String mnick);


}
