package com.rix.tossit.validation;

import com.rix.tossit.entity.GameHistory;
import com.rix.tossit.exception.InvalidMoveException;

public interface ValidatorSvc {
	
	void validateMove(GameHistory prev, GameHistory attempted) throws InvalidMoveException;

}
