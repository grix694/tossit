package com.rix.tossit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rix.tossit.entity.Game;
import com.rix.tossit.entity.GameHistory;

@Repository
public interface GameHistoryRepository extends CrudRepository<GameHistory, Long>{
	
	//GameHistory findByGameHistory(GameHistory gameHistory);
	
	List<GameHistory> findByGame(Game game);
	
}
