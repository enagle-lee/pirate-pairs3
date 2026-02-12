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
        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        game.deck.shuffle();
        System.out.println(game.deck.displayDeck());
        game.round1();
        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println("Player " + i + ":" + game.players[i].displayHand());
        }
        //testing out fold and discard code
        game.players[0].fold(game);
        System.out.println("Discard Pile: " + game.deck.displayDiscardPile());
        System.out.println("Player 0" + ":" + game.players[0].displayHand());


    }

    private void round1(){
        for (Player player : players) {
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

    public int getLowestCardInAllHands(){
        int lowest = Integer.MAX_VALUE;
        for(Player player : players){
            int[] hand = player.getHand();
            for (int card : hand) {
                if (card < lowest) {
                    lowest = card;
                }
            }
        }
        return lowest;
    }

    // private void turn(int playerNum){
        
    //     //make new hand array below


    //     if (players[playerNum].getScore() > losingScore) { 
    //         players[playerNum].setStatus(false);
    //     }

    // }
}
