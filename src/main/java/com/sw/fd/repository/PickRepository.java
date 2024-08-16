package com.sw.fd.repository;

import com.sw.fd.entity.Member;
import com.sw.fd.entity.Pick;
import com.sw.fd.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PickRepository extends JpaRepository<Pick, Integer> {
    Pick findByMemberAndStore(Member member, Store store);

/*    @Modifying
    @Transactional
    @Query("UPDATE Pick p SET p.pfolder = NULL WHERE p.pfolder.pfno = :pfno")
    void updatePfnoToNull(@Param("pfno") int pfno);*/

    // pick수 계산을 위해 추가한 부분 (다혜)
    @Query("SELECT COUNT(p) FROM Pick p WHERE p.store.sno = :sno")
    int countBySno(@Param("sno") int sno);
}