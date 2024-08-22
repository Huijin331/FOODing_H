package com.sw.fd.repository;

import com.sw.fd.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Integer> {
    // Invite 엔티티를 ino로 찾는 메소드
    Invite findByIno(int ino);

    // 초대받는 회원의 mno로 초대 목록을 조회하는 메서드
    List<Invite> findByMember_Mno(int mno);
}
