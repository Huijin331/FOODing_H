package com.sw.fd.service;

import com.sw.fd.entity.Member;
import com.sw.fd.entity.Pick;
import com.sw.fd.entity.Store;
import com.sw.fd.repository.MemberRepository;
import com.sw.fd.repository.PickRepository;
import com.sw.fd.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PickService {

    @Autowired
    private PickRepository pickRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StoreRepository storeRepository;


    public boolean togglePick(int mno, int sno) {
        Member member = memberRepository.findByMno(mno).orElse(null);
        Store store = storeRepository.findBySno(sno).orElse(null);

        Pick existingPick = pickRepository.findByMemberMnoAndStoreSno(mno, sno);
        if (existingPick != null) {
            pickRepository.delete(existingPick);
            return false;
        } else {
            Pick newPick = new Pick(member, store);
            pickRepository.save(newPick);
            return true;
        }
    }

    public boolean isPicked(int mno, int sno) {
        Pick existingPick = pickRepository.findByMemberMnoAndStoreSno(mno, sno);
        return existingPick != null;
    }
}
