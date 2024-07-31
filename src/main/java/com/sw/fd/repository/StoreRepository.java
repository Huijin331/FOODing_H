package com.sw.fd.repository;

import com.sw.fd.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findBySno(int sno);
    List<Store> findByScate(String scate);
    List<Store> findAll();
}
