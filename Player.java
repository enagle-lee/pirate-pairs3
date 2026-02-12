import java.util.Arrays;

public class Player {
    private int points = 0;
    private int[] hand = new int[0];

    private boolean inGame = true;


    // Action Methods

    public int[] takeCard(int cardDrawn){
        int[] newHand = new int[hand.length + 1];
        for (int i = 0; i < hand.length; i ++){
            newHand[i] = hand[i];
        }
        newHand[newHand.length - 1] = cardDrawn;
        hand = newHand; // reassign reference
        return hand;
    }

    public int[] fold(Game game){
        game.deck.discard(hand); //add hand to discard pile
        hand = new int [0];
        int penaltyCard = game.getLowestCardInAllHands();
        points += penaltyCard;
        return hand;
    }

    // Strategy Methods
    // public void strategy(){

    // }

    // Accessor Methods


    public int getPoints(){
        return points;
    }

    public String displayHand(){
        return Arrays.toString(hand);
    }

    public int[] getHand(){
        return hand;
    }

    public void setStatus(boolean inGame) {
        inGame = this.inGame;
    }
}
