package com.rix.tossit.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rix.tossit.entity.Game;
import com.rix.tossit.entity.GameStatus;
import com.rix.tossit.entity.User;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
	
	List<Game> findByPlayerOne(User playerOneId);
	
	//List<Game> findByPlayerOneIdAndGameStatus(long playerOneId, GameStatus gameStatus);
	
	List<Game> findByPlayerTwo(User playerTwoId);
	
	//List<Game> findByPlayerTwoIdAndGameStatus(long playerTwoId, GameStatus gameStatus);

}
