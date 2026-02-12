public class Game {
    int numPlayers = 3;
    int losingScore = (60 / numPlayers) + 1;
    Player[] players = new Player[numPlayers];
    Deck deck;

    boolean playGame = true;

    public static void main(String[] args) {
        Game game = new Game();
        for (int i = 0; i < game.numPlayers ; i++){
            game.players[i] = new Player();
        }
        game.deck = new Deck(10);
        game.deck.getDeck();
        System.out.println(game.deck.getTotCards());
        game.deck.shuffle();
        game.deck.getDeck();
        game.round1();
        game.deck.getDeck();
        System.out.println(game.deck.getTotCards());
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println(game.players[i].getHand());
        }
        
        //while (playGame) {
        //    round();
        //}

        



       

        
    }

    private void round1(){
        for (Player player: players) {
            player.takeCard(deck.drawCard());
        }
    }

    private void round() {
        for (int i = 0; i < players.length; i++){
            
            // call strategy method --> strategy method will either call draw method or pass method

            //turn(i);
        }

        //make new player array.  or more efficient to just check boolean?

    }

    // private void turn(int playerNum){
        
    //     //make new hand array below


    //     if (players[playerNum].getScore() > losingScore) { 
    //         players[playerNum].setStatus(false);
    //     }

    // }
}
