package com.sw.fd.service;

import com.sw.fd.entity.Pick;
import com.sw.fd.repository.PickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PickService {

    @Autowired
    private PickRepository pickRepository;


    public boolean togglePick(int mno, int sno) {
        Pick existingPick = pickRepository.findByMnoAndSno(mno, sno);
        if (existingPick != null) {
            pickRepository.delete(existingPick);
            return false;
        } else {
            Pick newPick = new Pick(mno, sno);
            pickRepository.save(newPick);
            return true;
        }
    }

    public boolean isPicked(int mno, int sno) {
        Pick existingPick = pickRepository.findByMnoAndSno(mno, sno);
        return existingPick != null;
    }
}
