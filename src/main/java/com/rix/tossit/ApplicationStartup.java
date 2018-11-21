package com.rix.tossit;


import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.rix.tossit.entity.Game;
import com.rix.tossit.entity.GameHistory;
import com.rix.tossit.entity.GameStatus;
import com.rix.tossit.entity.User;
import com.rix.tossit.exception.InvalidMoveException;
import com.rix.tossit.svc.GameSvc;
import com.rix.tossit.svc.UserSvc;
import com.rix.tossit.validation.BasicGameValidator;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{
	
	@Autowired
	UserSvc userSvc;
	
	@Autowired
	GameSvc gameSvc;
	
	@Autowired
	BasicGameValidator validator;
	
	private static final String BOARD_OPENING = "WEEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG";
	private static final String SECOND_MOVE =   "WEEWWEEWEWEEEEWEEEWEEWEEEEEYWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG";
	
	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		User gavin = createUser("Gavin");
		User fop = createUser("Fop");
		Game game = createGame(gavin, fop);
		List<GameHistory> gameHistories =  createGameHistories(game);
	}
	
	
	
	private User createUser(String userName) {
		//User Tests
		User user1 = new User();
		user1.setUserName(userName);
		user1.setPwd("p");
		
		user1 = userSvc.create(user1);
		
		return user1;
	}
	
	private Game createGame(User p1, User p2) {
		Game g1 = new Game(p1, p2, GameStatus.REQUESTED_BY_P1);
		
		g1 = gameSvc.create(g1);

		return g1;
	}
	
	public List<GameHistory> createGameHistories(Game game) {
		List<GameHistory> gameHistories;
		
		game = gameSvc.addMove(game, BOARD_OPENING, game.getPlayerOne());
		game = gameSvc.addMove(game, SECOND_MOVE, game.getPlayerTwo());
		game = gameSvc.getGame(game);
		gameHistories = game.getGameHistories();
		
		
		try {
			validator.validateMove(gameHistories.get(0), gameHistories.get(1));
		} catch (InvalidMoveException e) {
			e.printStackTrace();
		};
		
		
		System.out.println("Game Histories for Game: " + game.getGameId());
		for(GameHistory gh: game.getGameHistories()) {
			System.out.println("Game Hist info");
			System.out.println("Piece loc" + gh.getPieceLocation());
			System.out.println("Date:" + gh.getDate());
			System.out.println("Game ID: " + gh.getGame().getGameId());
			System.out.println("GH ID:" + gh.getGameHistoryId());
			System.out.println("Last Move:" + gh.getLastMove().getUserName());
		}
		
		
		return gameHistories;
	}
	
}