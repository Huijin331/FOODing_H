package com.sw.fd.repository;

import com.sw.fd.entity.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickRepository extends JpaRepository<Pick, Integer> {
    Pick findByMnoAndSno(int mno, int sno);
}