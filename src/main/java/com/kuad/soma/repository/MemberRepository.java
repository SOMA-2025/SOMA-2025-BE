package com.kuad.soma.repository;

import com.kuad.soma.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.name LIKE %:keyword%")
    List<Member> searchByNameContaining(@Param("keyword") String keyword);
}
