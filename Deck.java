import java.util.Arrays;

public class Deck {
    private int highestCard;
    private int totCards = 0;
    private int[] deck;
    private int [] discardPile = new int[0];

    public Deck(int highestCard) {
        this.highestCard = highestCard;
        for (int i = 1; i <= highestCard; i++) {
            totCards += i;
        }
        // make an array for cards yet to be drawn
        deck = new int[totCards];
        int tempLoc = 0;
        for (int i = 1; i <= highestCard; i++) {
            for (int j = 0; j < i; j++) {
                deck[tempLoc] = i;
                tempLoc++;
            }
        }
    }

// array for discarded cards

// remove card function

// shuffle function
    public void shuffle() {
        for (int i = 0; i < totCards; i ++) {
            int a = (int) (Math.random() * totCards);
            int b = (int) (Math.random() * totCards);
            if (a != b) {
            int tempValue = deck[a];
            deck[a] = deck[b];
            deck[b] = tempValue;
            }
        }
    }

    // give player a take card function that takes this method as an input
    public int drawCard(){
        int drawn = deck[deck.length - 1];   // take top card
        int[] newDeck = new int[deck.length - 1];
        for (int i = 0; i < newDeck.length; i++){
            newDeck[i] = deck [i];
        }
        deck = newDeck; // reassign reference
        return drawn;
    }

    public int[] discard(int[] hand){
        int[] newDiscardPile = new int[discardPile.length + hand.length];
        for (int i = 0; i < discardPile.length; i++){
            newDiscardPile[i] = discardPile[i];
        }
        int tracker = 0;
        for (int i = discardPile.length; i < newDiscardPile.length; i++){
            newDiscardPile[i] = hand[tracker];
            tracker++;
        }
        discardPile = newDiscardPile; //reassign
        return discardPile;
    }



    // getters

    public int getHighestCard() {
        return highestCard;
    }

    public int getTotCards() {
        return totCards;
    }

    public String displayDeck() {
        return Arrays.toString(deck);
    }

}

// player as input??
// public int drawCard(){

// return deck[deck.length - 1];
// }
