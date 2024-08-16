package com.sw.fd.service;

import com.sw.fd.entity.Member;
import com.sw.fd.entity.Pfolder;
import com.sw.fd.entity.Pick;
import com.sw.fd.entity.Store;
import com.sw.fd.repository.MemberRepository;
import com.sw.fd.repository.PfolderRepository;
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

    @Autowired
    public PfolderRepository pfolderRepository;


    public boolean togglePick(int mno, int sno, int pfno) {
        Member member = memberRepository.findByMno(mno).orElseThrow(() -> new RuntimeException("로그인 정보를 불러오는 데 실패했습니다."));;
        Store store = storeRepository.findBySno(sno).orElseThrow(() -> new RuntimeException("가게 정보를 불러오는 데 실패했습니다."));;
        Pfolder pfolder = pfolderRepository.findByPfno(pfno).orElseThrow(() -> new RuntimeException("폴더 정보를 불러오는 데 실패했습니다."));;


        Pick existingPick = pickRepository.findByMemberAndStore(member, store);
        if (existingPick != null) {
            pickRepository.delete(existingPick);
            return false;
/*            try {
                // 참조된 레코드를 먼저 처리 (예: pfno를 NULL로 설정)
                pickRepository.updatePfnoToNull(existingPick.getPno());

                // 그런 다음 레코드 삭제
                pickRepository.delete(existingPick);
            } catch (DataIntegrityViolationException e) {
                // 외래 키 제약 조건 위반 시 처리
                throw new RuntimeException("Error while deleting the pick due to foreign key constraint", e);
            } catch (Exception e) {
                // 일반적인 예외 처리
                throw new RuntimeException("Error while deleting the pick", e);
            }
            return false;*/
        } else {
            Pick newPick = new Pick(member, store, pfolder);
            pickRepository.save(newPick);
            return true;
        }
    }

    public boolean isPicked(int mno, int sno) {
        Member member = memberRepository.findByMno(mno).orElseThrow(() -> new RuntimeException("로그인 정보를 불러오는 데 실패했습니다."));;
        Store store = storeRepository.findBySno(sno).orElseThrow(() -> new RuntimeException("가게 정보를 불러오는 데 실패했습니다."));;

        Pick existingPick = pickRepository.findByMemberAndStore(member, store);
        return existingPick != null;
    }
}
