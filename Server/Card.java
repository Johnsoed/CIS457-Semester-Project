package durakServer; 


public class Card {

	public enum Rank {


		SIX, 

		SEVEN, 

		EIGHT, 

		NINE,

		TEN, 

		JACK, 

		QUEEN, 

		KING, 

		ACE
	}


	public enum Suit {

		CLUBS, 

		DIAMONDS, 

		HEARTS, 

		SPADES
	}


	private final Rank value;

	private final Suit suit;

	private String filename;
	
	private int score;



	public Card(Rank value, Suit suit) {
		super();
		this.value = value;
		this.suit = suit;
		this.filename = filename;
        switch (rank()) {
			case SIX:
				this.score = 6;
				break;
			case SEVEN:
				this.score = 7;
				break;
			case EIGHT:
				this.score = 8;
				break;
			case NINE:
				this.score = 9;
				break;
			case TEN:
				this.score = 11;
				break;
			case JACK:
				this.score = 12;
				break;
			case QUEEN:
				this.score = 13;
				break;
			case KING:
				this.score = 14;
				break;
			case ACE:
				this.score = 15;
				break;
			}
	}

	public Rank rank() {
		return value;
	}


	public Suit suit() {
		return suit;
	}


	public String toString() {
		return value + " of " + suit;
	}
	
	public int getScore() {
        return score;
	}
	
}
