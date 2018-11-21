package com.rix.tossit.svc;

import java.util.List;

import com.rix.tossit.entity.Game;
import com.rix.tossit.entity.GameStatus;
import com.rix.tossit.entity.User;

public interface GameSvc {
	
	Game create(Game game); 
	
	Game getGame(Game game);
	
	List<Game> getGames(User user);
	
	List<Game> getGames(User user, GameStatus gameStatus);
	
	Game addMove(Game game, String pieceLoc, User lastMove);
	
	Game endGame(GameStatus gameStatus);
	
	
}
