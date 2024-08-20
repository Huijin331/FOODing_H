package com.sw.fd.repository;

import com.sw.fd.entity.Alarm;
import com.sw.fd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    // 로그인한 회원이 받은 모든 알림 조회
    List<Alarm> findByMember(Member member);

    // 회원 ID로 알림 조회
    List<Alarm> findByMember_Mid(String memberId);
}
