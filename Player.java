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

    public int[] foldResult(int penaltyCard){
        //game.deck.discard(hand); //add hand to discard pile
        points += penaltyCard;
        hand = new int [0];
        return hand;
    }



    public int hasDouble(){ //if false returns -1.  otherwise should add to points.  In game class have it call the fold method.
        for (int i = 0; i < hand.length - 1; i++) {
            for (int j = i + 1; j < hand.length; j++){
                if (hand[i] == hand[j]){
                    return hand[i];
                }
            }
        }
        return -1;
    }
       
    // Strategy Methods

    // Return a boolean>.  game then does something with the boolean 
    public boolean willDraw(){
        if (hand.length == 0) {
            return true;
        }
        else if(hand.length > 3){
            return false;
        }
        else{
            return true;
        }
    }

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

    public boolean getStatus(){
        return inGame;
    }

    public void setStatus(boolean inGame) {
        this.inGame = inGame;
    }
}
