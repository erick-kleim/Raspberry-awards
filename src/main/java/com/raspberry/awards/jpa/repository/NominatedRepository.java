package com.raspberry.awards.jpa.repository;


import com.raspberry.awards.jpa.model.Nominated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NominatedRepository extends JpaRepository<Nominated, Long> {
    List<Nominated> findByWinnerOrderByYear(boolean isWinner);
}
