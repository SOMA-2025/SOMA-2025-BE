package com.kuad.soma.repository;

import com.kuad.soma.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String name);
    Team findByPageKey(String pageKey);
}
