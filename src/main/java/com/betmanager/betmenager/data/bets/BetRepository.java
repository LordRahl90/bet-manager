package com.betmanager.betmenager.data.bets;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.UUID;
import java.util.List;

public interface BetRepository extends JpaRepository<Bet, UUID> {


    @Query(value = "SELECT * FROM Bet b LIMIT :page, :limit", nativeQuery = true)
    List<Bet> paginate(@Param("page") int page, @Param("limit") int limit);
}
