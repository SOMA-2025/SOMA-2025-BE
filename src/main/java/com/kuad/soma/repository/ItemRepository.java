package com.kuad.soma.repository;

import com.kuad.soma.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByTeamId(Long teamId);
    List<Item> findByTeam_Name(String teamName);
}
