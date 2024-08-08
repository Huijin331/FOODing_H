package com.sw.fd.repository;

import com.sw.fd.entity.Store;
import com.sw.fd.entity.StoreTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreTagRepository extends JpaRepository<StoreTag, Integer> {
    List<StoreTag> findByStore_SnoAndTag_Tno(int sno, int tno);

    List<StoreTag> findByStore_Sno(int sno);
}
