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

    // shuffle function
    public void shuffle() {
        for (int i = 0; i < deck.length; i ++) {
            int a = (int) (Math.random() * deck.length);
            int b = (int) (Math.random() * deck.length);
            if (a != b) {
            int tempValue = deck[a];
            deck[a] = deck[b];
            deck[b] = tempValue;
            }
        }
    }

    public void restockDeck(){
        int[] newDeck = new int[discardPile.length];
        for (int i = 0; i < newDeck.length; i++){
            newDeck[i] = discardPile[i];
        }
        deck = newDeck; // reassign
        shuffle();
        discardPile = new int[0]; // clear discard pile
        System.out.println("Deck restocked!");
    }

    // removes card from the array and returns the int
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

    public int getHighestCard(){
        return highestCard;
    }

    public int getTotCards(){
        return totCards;
    }

    public int getNumCardsInDeck(){
        return deck.length;
    }

    public int getNumCardsInDiscardPile(){
        return discardPile.length;
    }

    public int[] getDeck(){
        return deck;
    }

    public int[] getDiscardPile(){
        return discardPile;
    }

    public String displayDeck(){
        return Arrays.toString(deck);
    }

    public String displayDiscardPile(){
        return Arrays.toString(discardPile);
    }
}