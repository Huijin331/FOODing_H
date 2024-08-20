package com.sw.fd.repository;

import com.sw.fd.entity.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Integer> {
    // Invite 엔티티를 ino로 찾는 메소드
    Invite findByIno(int ino);
}
