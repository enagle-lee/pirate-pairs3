public class Player {
    private int score = 0;
    private int[] hand = new int[1];

    private boolean inGame = true;

    Player(int firstCard){
        hand[0] = firstCard;
    }

    public int getScore(){
        return score;
    }


    public int[] getHand(){
        return hand;
    }

    public void setStatus(boolean inGame) {
        inGame = this.inGame;
    }
}
