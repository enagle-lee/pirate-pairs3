public class Game {
    static int highestCard = 10;
    static int numPlayers = 3;
    static int losingScore = (60 / numPlayers) + 1;
    static Player[] players = new Player[numPlayers];

    boolean playGame = true;

    public static void main(String[] args) {

        while (playGame) {
            round();
        }

        



       

        
    }

    private static void round() {
        for (int i = 0; i < players.length; i++){
            turn(i);
        }

        //make new player array.  or more efficient to just check boolean?

    }

    private static void turn(int playerNum){
        int cardDrawn = (int) (Math.random() * highestCard) + 1;
        //make new hand array below


        if (players[playerNum].getScore() > losingScore) { 
            players[playerNum].setStatus(false);
        }

    }
}
