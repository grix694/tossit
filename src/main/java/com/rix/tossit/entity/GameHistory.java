package com.rix.tossit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class GameHistory {
	
	@Id
	@NotNull
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long gameHistoryId;
	
	@ManyToOne
	@JoinColumn(name="gameId", nullable=false)
	private Game game;
	
	@Column
	private Date date;
	
	@OneToOne
	@JoinColumn(name="lastMoveId")
	private User lastMove;
	
	@Column(nullable=false)
	private String pieceLocation;
	
	public GameHistory() {};
	
	public GameHistory(Game game, Date date, String pieceLocation, User lastMove) {
		this.game = game;
		this.date = date;
		this.pieceLocation = pieceLocation;
		this.lastMove = lastMove;
	}
	
	public long getGameHistoryId() {
		return gameHistoryId;
	}

	public void setGameHistoryId(long gameHistoryId) {
		this.gameHistoryId = gameHistoryId;
	}

	public Game getGame() {
		return game;
	}

	public void setGameId(Game game) {
		this.game = game;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPieceLocation() {
		return pieceLocation;
	}

	public void setPieceLocation(String pieceLocation) {
		this.pieceLocation = pieceLocation;
	}

	public User getLastMove() {
		return lastMove;
	}

	public void setLastMove(User lastMove) {
		this.lastMove = lastMove;
	}
	
	
}
