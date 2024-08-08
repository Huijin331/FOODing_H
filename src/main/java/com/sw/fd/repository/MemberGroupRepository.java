package com.sw.fd.repository;

import com.sw.fd.entity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Integer> {
    @Query("SELECT mg FROM MemberGroup mg WHERE mg.group.gno IN :gnos")
    List<MemberGroup> findByGroupGnoIn(@Param("gnos") List<Integer> gnos);

    // 특정 그룹(gno)과 회원(mid)으로 회원이 그룹에 존재하는지 확인하는 메서드
    @Query("SELECT CASE WHEN COUNT(mg) > 0 THEN true ELSE false END " +
            "FROM MemberGroup mg WHERE mg.group.gno = :gno AND mg.member.mid = :mid")
    boolean existsByGroupGnoAndMemberMid(@Param("gno") int gno, @Param("mid") String mid);

    @Query("SELECT mg " +
            "FROM MemberGroup mg " +
            "WHERE mg.group.gno = :gno AND mg.member.mid = :mid")
    MemberGroup findByGroupGnoAndMemberMid(@Param("gno") int gno, @Param("mid") String mid);

    @Query("SELECT mg FROM MemberGroup mg WHERE mg.member.mid = :mid AND mg.jauth = :jauth")
    List<MemberGroup> findByMemberMidAndJauth(@Param("mid") String memberId, @Param("jauth") int jauth);

 }