package com.sw.fd.repository;

import com.sw.fd.entity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Integer> {
    @Query("SELECT mg FROM MemberGroup mg WHERE mg.group.gno IN :gnos")
    List<MemberGroup> findByGroupGnoIn(@Param("gnos") List<Integer> gnos);
}