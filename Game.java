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


        game.round();
        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println("Player " + i + ":" + game.players[i].displayHand());
        }

        game.round();
        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println("Player " + i + ":" + game.players[i].displayHand());
        }

        game.round();
        System.out.println("Discard Pile: " + game.deck.displayDiscardPile());

        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println("Player " + i + ":" + game.players[i].displayHand());
        }
        System.out.println("Discard Pile: " + game.deck.displayDiscardPile());

    }

    private void round1(){
        for (Player player : players) {
            drawAction(player);
        }
    }

    private void round() {
        for (int i = 0; i < players.length; i++){
            if (players[i].getStatus()){
                turn(i);
            }            
        }

        //make new player array.  or more efficient to just check boolean? NEEDS TO KNOW IF PLAYERIS EVEN PLAYING

    }

    // turn method.  calls the boolean returning strat functions in player.  checks for doubles after.  if points are added, check if its a valid score

    private void turn(int i){
        if (players[i].willDraw()){
            drawAction(players[i]);
            if(players[i].hasDouble() > 0){
                foldAction(players[i], players[i].hasDouble());
            }
        }else{ // eats points
            foldAction(players[i], getLowestCardInAllHands());
        }
        if (players[i].getPoints() > losingScore) { 
            players[i].setStatus(false);
        }
    }

    //Potential Player Actions

    public void drawAction(Player player){
        player.takeCard(deck.drawCard());
    }

    //add folded hand to discard pile
    public void foldAction(Player player, int penaltyCard){
        deck.discard(player.getHand()); //returns updated discard pile
        player.foldResult(penaltyCard); //updates player's points and hand
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
