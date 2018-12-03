package durakClient; 


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

	private String valueString;
	private String suitString;
	
	private int score;



	public Card(Rank value, Suit suit) {
		super();
		this.value = value;
		this.suit = suit;
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
				this.score = 10;
				break;
			case JACK:
				this.score = 11;
				break;
			case QUEEN:
				this.score = 12;
				break;
			case KING:
				this.score = 13;
				break;
			case ACE:
				this.score = 14;
				break;
			}
	}
	
	
	public Card(String valueString, String suitString) {
		super();
		this.valueString = valueString;
		this.suitString = suitString;
        switch (valueString) {
			case "SIX":
                this.value = Rank.SIX;
				this.score = 6;
				break;
			case "SEVEN":
                this.value = Rank.SEVEN;
				this.score = 7;
				break;
			case "EIGHT":
                this.value = Rank.EIGHT;
				this.score = 8;
				break;
			case "NINE":
                this.value = Rank.NINE;
				this.score = 9;
				break;
			case "TEN":
                this.value = Rank.TEN;
				this.score = 10;
				break;
			case "JACK":
                this.value = Rank.JACK;
				this.score = 11;
				break;
			case "QUEEN":
                this.value = Rank.QUEEN;
				this.score = 12;
				break;
			case "KING":
                this.value = Rank.KING;
				this.score = 13;
				break;
			case "ACE":
                this.value = Rank.ACE;
				this.score = 14;
				break;
            default: 
                this.value = Rank.SIX;
                this.score = 6;
                break;
				
            
			}
			
			
        switch (suitString) {
			case "CLUBS":
                this.suit = Suit.CLUBS;
				break;
			case "DIAMONDS":
                this.suit = Suit.DIAMONDS;
				break;
			case "HEARTS":
                this.suit = Suit.HEARTS;
				break;
			case "SPADES":
                this.suit = Suit.SPADES;
				break;
            default: 
                this.suit = Suit.CLUBS;
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
