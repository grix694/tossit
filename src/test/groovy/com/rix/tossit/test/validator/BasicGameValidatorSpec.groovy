package com.rix.tossit.test.validator

import com.rix.tossit.entity.Game
import com.rix.tossit.entity.GameHistory
import com.rix.tossit.entity.User
import com.rix.tossit.validation.BasicGameValidator
import com.rix.tossit.validation.ValidatorSvc
import com.rix.tossit.entity.GameStatus
import com.rix.tossit.exception.InvalidMoveException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification
import java.util.Calendar;

@Ignore
@ContextConfiguration
class BasicGameValidatorSpec extends Specification {
	
	static def validator
	
	static def game
	static def player1
	static def player2
	static def cal
	static def initGH
	
	static def initialBoardPos = "WEEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG"
	//"WEEYWEEW EWEEEEWE EEWEEWEE EEEWWEEE EEEGGEEE EEGEEGEE EGEEEEGE GEEGXEEG"
	
	def setupSpec() {
		validator = new BasicGameValidator()
		player1 = new User("User1", "pwd")
		player2 = new User("User2", "pwd2")
		game = new Game(player1, player2, GameStatus.REQUESTED_BY_P1)
		cal = Calendar.getInstance()
		initGH = new GameHistory(game, cal.getTime(), initialBoardPos, player1)
	}
	
	def "Test Good Column Movement of Pawn"() {
		given: "Some game histories with board locations representing a column move of a pawn"
		def gh = new GameHistory(game, cal.getTime(), move, player2)
		
		when: "Validating the moves"
		validator.validateMove(initGH, gh)
		
		
		then: "No Exceptions are thrown"
		noExceptionThrown()
		
		where:
		move | _
		"EEEYWEEWWWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white forward 1 space 
		"EEEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEWGEEEEGEGEEGXEEG" |_ //Move white forward 6 spaces
		"WWEYWEEWEEEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white backward 1 space
		"WEEYWEEWEWEWEEWEEEWEEWEEEEEEWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white backward 2 spaces 
	}
	
	def "Test Good Row Movement of Pawn" () {
		given: "Some game histories with board loactions representing a row move of a pawn"
		def gh = new GameHistory(game, cal.getTime(), move, player2)
		
		when: "Validating the moves"
		validator.validateMove(initGH, gh)
		
		then: "No Exceptions are thrown"
		noExceptionThrown()
		
		where:
		move | _
		"EWEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white "left" one space
		"WEEYWEWEEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white "right" one space
		"WEEYWEEWEWEEEEWEEEWEEWEEWEEEWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white "left" three spaces
		"WEEYWEEWEWEEEEWEEEWEEWEEEEEWEEEWEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_ //Move white "right" three spaces
	}
	
	def "Test Good Diagonal Movement of Pawn" () {
		given: "Some game histories with board loactions representing a diagonal move of a pawn"
		def gh = new GameHistory(game, cal.getTime(), move, player2)
		
		when: "Validating the moves"
		validator.validateMove(initGH, gh)
		
		then: "No Exceptions are thrown"
		noExceptionThrown()
		
		where:
		move | _
		"WEEYWEEWEEEEEEWEWEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "forward-right" one space
		"WEEYWEEWEWEEEEWEEEWEEWEEEEEEWEEEEEEGGEEEEEGEEGEEWGEEEEGEGEEGXEEG" |_  //Move white "forward-right" three spaces
		"WEEYWEEWEWEEEEEEEEWEEWEWEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "forward-left" one space
		"WEEYEEEWEWEEEEWEEEWEEWEEEEEWWEEWEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "forward-left" three spaces
		"WEEYWWEWEWEEEEEEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "backward-right" one space
		"WWEYWEEWEWEEEEWEEEWEEWEEEEEWEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "backward-right" three spaces
		"WEEYWEEWEWEWEEWEEEEEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "backward-left" one space
		"WEEYWEWWEWEEEEWEEEWEEWEEEEEEWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" |_  //Move white "backward-left" three spaces
	}
	
	def "Test Good Movement of Ball Only" () {
		given: "Some game histories with board loactions representing a diagonal move of a pawn"
		def gh1 = new GameHistory(game, cal.getTime(), firstMove, player1)
		def gh2 = new GameHistory(game, cal.getTime(), secondMove, player2)
		
		when: "Validating the moves"
		validator.validateMove(gh1, gh2)
		
		then: "No Exceptions are thrown"
		noExceptionThrown()
		
		where:
		firstMove       												   | secondMove
		initialBoardPos 												   | "WEEWWEEWEWEEEEWEEEWEEWEEEEEYWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Forward movement of ball
		"WEEWWEEWEWEEEEWEEEWEEWEEEEEYWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos 													// Backward movement of ball
		initialBoardPos													   | "WEEWYEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Left movement of ball
		"WEEWYEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos 	      											// Right movement of ball
		"WEEYWEEWEWEEEEWEEWEEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEWEWEEEEWEEYEEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Forward-Right movement of ball
		"WEEWWEEWEWEEEEWEEYEEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEYWEEWEWEEEEWEEWEEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Backward-Right movement of ball
		initialBoardPos													   | "WEEWWEEWEWEEEEWEEEWEEYEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Forward-Left movement of ball
		"WEEWWEEWEWEEEEWEEEWEEYEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos 	      											// Backward-Left movement of ball
	}
	
	def "Test Good Movement of Ball and Pawn" () {
		given: "Some game histories with board loactions representing a diagonal move of a pawn"
		def gh1 = new GameHistory(game, cal.getTime(), firstMove, player1)
		def gh2 = new GameHistory(game, cal.getTime(), secondMove, player2)
		
		when: "Validating the moves"
		validator.validateMove(gh1, gh2)
		
		then: "No Exceptions are thrown"
		noExceptionThrown()
		
		where:
		firstMove       												   | secondMove
		initialBoardPos													   | "WEYEWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //Right Ball-Pawn movement
		"WEYEWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos 													//Left Ball-Pawn movement
		initialBoardPos 												   | "WEEEWEEWEWEYEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //Forward Ball-Pawn movement
		"WEEEWEEWEWEYEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos													//Backward Ball-Pawn movement
		initialBoardPos													   | "WEEEWEEWEWYEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //Forward-Right Ball-Pawn movement
		"WEEEWEEWEWYEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos													//Backward-Left Ball-Pawn movement
		initialBoardPos													   | "WEEEWEEWEWEEYEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //Forward-Right Ball-Pawn movement
		"WEEEWEEWEWEEYEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | initialBoardPos													//Backward-Leftt Ball-Pawn movement
	}
	
	def "Test Bad Movement of Ball and Pawn" () {
		given: "Some game histories with board loactions"
		def gh1 = new GameHistory(game, cal.getTime(), firstMove, player1)
		def gh2 = new GameHistory(game, cal.getTime(), secondMove, player2)
		
		when: "Validating the moves"
		validator.validateMove(gh1, gh2)
		
		then: "No Exceptions are thrown"
		thrown(InvalidMoveException)
		
		where:
		firstMove       												   | secondMove															
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WYWEEEEEWEWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Backward Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WWWEEEEEWEWEEEEEWYWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Forward Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WWWEEEEEWEYEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Left Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WWWEEEEEYEWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Right Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "YWWEEEEEWEWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Backward-Right Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WWYEEEEEWEWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Backward-Left Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WWWEEEEEWEWEEEEEWWYEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Forward-Left Collision
		"WWWEEEEEWYWEEEEEWWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WWWEEEEEWEWEEEEEYWWEEEEEEEEEEEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // Forward-Right Collision
	}
	
	def "Test Bad Movement of Ball Only" () {
		given: "Some game histories with board loactions"
		def gh1 = new GameHistory(game, cal.getTime(), firstMove, player1)
		def gh2 = new GameHistory(game, cal.getTime(), secondMove, player2)
		
		when: "Validating the moves"
		validator.validateMove(gh1, gh2)
		
		then: "No Exceptions are thrown"
		thrown(InvalidMoveException)
		
		where:
		firstMove       												   | secondMove
		"WEEYWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEYEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //White Toss Left over white piece
		"WEEWYEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "YEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //White Toss Right over white piece
		"WEEWYEEWEWEEWEEEEEWEWEEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEWEWEEWEEEEEWEYEEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //White Toss Forward over white piece
		"WEEWWEEWEWEEWEEEEEWEYEEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWYEEWEWEEWEEEEEWEWEEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // White Toss Backward over white piece
		"YEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEWEWEEEEWEEEYEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" // White Toss Forward-Left over white
		"WEEWWEEWEWEEEEWEEEYEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "YEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //White Toss Backward-Right over white
		"WEEWWEEYEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEWEWEEEEWEEEWEEYEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //White Toss Forward-Right over white
		"WEEWWEEWEWEEEEWEEEWEEYEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEYEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //White Toss Backward-Left over white
		"YEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" | "WEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEYEEGXEEG" //White Toss to Green
		"WEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEYEEGXEEG" | "YEEWWEEWEWEEEEWEEEWEEWEEEEEWWEEEEEEGGEEEEEGEEGEEEGEEEEGEGEEGXEEG" //Green Toss to White
	}
	
//	def "Test Bad Movement of Pawn Only" () {
//		given: "Some game histories with board loactions"
//		def gh1 = new GameHistory(game, cal.getTime(), firstMove, player1)
//		def gh2 = new GameHistory(game, cal.getTime(), secondMove, player2)
//		
//		when: "Validating the moves"
//		validator.validateMove(gh1, gh2)
//		
//		then: "No Exceptions are thrown"
//		thrown(InvalidMoveException)
//		
//		where:
//		firstMove       												   | secondMove
//	}
	/*
	 *  errors to test
	 * 
	 * Too many pieces
	 * too few
	 * bad movement "L" shape 
	 * collision
	 * tossing over
	 * ball disapears
	 * toss over/move over opposite pieces
	 * did someone win?
	 */
}
