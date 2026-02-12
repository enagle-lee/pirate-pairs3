import java.util.Arrays;

public class Player {
    private int points = 0;
    private int[] hand = new int[0];

    private boolean inGame = true;


    // public void strategy(){

    // }

    public int[] takeCard(int cardDrawn){
        int[] newHand = new int[hand.length + 1];
        for (int i = 0; i < hand.length - 1; i ++){
            newHand[i] = hand[i];
        }
        newHand[newHand.length - 1] = cardDrawn;

        return hand;
    }

    public int getPoints(){
        return points;
    }

    public String getHand(){
        Arrays.toString(hand);
    }

    public void setStatus(boolean inGame) {
        inGame = this.inGame;
    }
}
