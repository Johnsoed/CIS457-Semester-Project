package durakProject;
import java.util.*;
import durakProject.Card.Rank;
import durakProject.Card.Suit;


public class Deck {


	private List<Card> deckList;


	public Deck() {
		newDeck();
		Collections.shuffle(deckList);
	}


	public Card deal() {
		// safety. so we don't pull from empty deck
		if (deckCount() > 0) {
			Card dealtCard;
			dealtCard = deckList.get(0);
			deckList = deckList.subList(1, deckCount());
			return dealtCard;
		} else {
			newDeck();
			Card dealtCard;
			dealtCard = deckList.get(0);
			deckList = deckList.subList(1, deckCount());
			return dealtCard;
		}
	}


	public final int deckCount() {
		return deckList.size();
	}


	public final void shuffleDeck() {
		Collections.shuffle(deckList);
	}


	public List<Card> getdeckList() { 
		return deckList;
	}
	
	public void newDeck() {
		deckList = new ArrayList<Card>();
		for (Suit suit : Suit.values()) {
			for (Rank value : Rank.values()) {
				deckList.add(new Card(value, suit));
			}
		}
	}

}
