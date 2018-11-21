package com.rix.tossit.svc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rix.tossit.entity.Game;
import com.rix.tossit.entity.GameHistory;
import com.rix.tossit.entity.GameStatus;
import com.rix.tossit.entity.User;
import com.rix.tossit.repository.GameHistoryRepository;
import com.rix.tossit.repository.GameRepository;

@Service
public class GameSvcImpl implements GameSvc {
	
	@Autowired
	GameRepository gameRepo;
	
	@Autowired
	GameHistoryRepository ghRepo;
	
	Calendar cal = Calendar.getInstance();
	
	@Override
	public Game create(Game game) {
		return gameRepo.save(game);
	}
	
	@Override
	public Game getGame(Game game) {
		
		return gameRepo.findOne(game.getGameId());
	}

	@Override
	public List<Game> getGames(User user) {
		List<Game> gameList = new ArrayList<>();
		gameList.addAll(gameRepo.findByPlayerOne(user));
		gameList.addAll(gameRepo.findByPlayerTwo(user));
		
		return gameList;
	}

	@Override
	public List<Game> getGames(User user, GameStatus gameStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game addMove(Game game, String pieceLoc, User lastMove) {
		GameHistory gh = new GameHistory(game, cal.getTime(), pieceLoc, lastMove);
		ghRepo.save(gh);
		
		return game;
	}

	@Override
	public Game endGame(GameStatus gameStatus) {
		// TODO Auto-generated method stub
		return null;
	}

}
