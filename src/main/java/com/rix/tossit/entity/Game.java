package com.rix.tossit.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="game")
public class Game {
	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long gameId;
	
	@OneToOne
	@JoinColumn(name="playerOneUserId")
	private User playerOne;
	
	@OneToOne
	@JoinColumn(name="playerTwoUserId")
	private User playerTwo;
	
	@Column
	@Enumerated(EnumType.STRING)
	private GameStatus gameStatus;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="game")	
	private List<GameHistory> gameHistories = new ArrayList<>();
	
	public Game() {};
	
	public Game(User playerOne, User playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
	
	public Game(User p1, User p2, GameStatus status) {
		this.playerOne = p1;
		this.playerTwo = p2;
		this.gameStatus = status;
		
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public User getPlayerOne() {
		return playerOne;
	}

	public void setPlayerOne(User playerOne) {
		this.playerOne = playerOne;
	}

	public User getPlayerTwo() {
		return playerTwo;
	}

	public void setPlayerTwo(User playerTwo) {
		this.playerTwo = playerTwo;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	public List<GameHistory> getGameHistories() {
		return gameHistories;
	}

	public void setGameHistories(List<GameHistory> gameHistories) {
		this.gameHistories = gameHistories;
	}
	
	
}
