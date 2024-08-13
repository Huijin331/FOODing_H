package com.sw.fd.repository;

import com.sw.fd.entity.Store;
import com.sw.fd.entity.StoreTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreTagRepository extends JpaRepository<StoreTag, Integer> {

    List<StoreTag> findByStore_Sno(int sno);
    List<StoreTag> findByTag_Tno(int tno);
    List<StoreTag> findByTag_tno(List<Integer> tnos);

    StoreTag findByStore_SnoAndTag_Tno(int tno, int sno);
}
