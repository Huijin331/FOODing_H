package com.sw.fd.service;

import com.sw.fd.entity.Invite;
import com.sw.fd.repository.InviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InviteService {

    @Autowired
    private InviteRepository inviteRepository;

    public void saveInvite(Invite invite) {
        inviteRepository.save(invite);
    }

    // ino로 Invite 엔티티를 조회하는 메소드
    public Invite getInviteByIno(int ino) {
        return inviteRepository.findByIno(ino);
    }
}
