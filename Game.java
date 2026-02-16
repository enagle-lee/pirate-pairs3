public class Game {
    int numPlayers = 3;
    int losingScore = (60 / numPlayers) + 1;
    Player[] players = new Player[numPlayers];
    int numPlayersOut = 0;
    Deck deck;

    boolean playGame = true;

    public static void main(String[] args) {
        Game game = new Game();
        for (int i = 0; i < game.numPlayers ; i++){
            game.players[i] = new Player();
            System.out.println(game.players[i].getStratLevel());
        }
        game.deck = new Deck(10);
        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        game.deck.shuffle();
        System.out.println(game.deck.displayDeck());
        System.out.println("Round 1 ------------------------------------ ");
        game.round1();
        System.out.println(game.deck.displayDeck());
        System.out.println(game.deck.getTotCards());
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println("Player " + i + ":" + game.players[i].displayHand());
        }
        int counter = 2;
        while(game.playGame){
            System.out.println("Round " + counter + " ------------------------------------ ");
            game.round();
            System.out.println(game.deck.displayDeck());
            System.out.println(game.deck.getTotCards());
            for (int i = 0; i < game.numPlayers; i++) {
                System.out.println("Player " + i + ":" + game.players[i].displayHand() + ". Points: " + game.players[i].getPoints());
            }
            System.out.println("Discard Pile: " + game.deck.displayDiscardPile());
            counter++;
        }
        for (int i = 0; i < game.players.length; i++) {
            if (game.players[i].getStatus()){
                System.out.println("Player " + i + " is the winner!");
            }
        }
    }

    private void round1(){
        for (Player player : players) {
            drawAction(player);
        }
    }

    private void round() {
        for (int i = 0; i < players.length; i++){
            if (!playGame) break;
            if (players[i].getStatus()){
                turn(i);
            }                 
        }
    }

    // turn method.  calls the boolean returning strat functions in player.  checks for doubles after.  if points are added, check if its a valid score

    private void turn(int i){
        int lowestCardInAllHands = getLowestCardInAllHands();
        if (players[i].getStratLevel() == "simple"){
            players[i].stratSimple(lowestCardInAllHands, losingScore);
        } else{ //if (players[i].getStratLevel() == "medium")
            players[i].stratMedium(lowestCardInAllHands, losingScore);
        } 
        if (players[i].getWillDraw()){
            drawAction(players[i]);
            if(players[i].hasDouble() > 0){
                foldAction(players[i], players[i].hasDouble());
            }
        }else{ // eats points
            //take lowest card from player who has it and add it to discard pile
            for (Player player : players) {
                if (player.removeCard(lowestCardInAllHands)){
                    deck.discard(new int[] {lowestCardInAllHands});
                    break;  // Stop after finding first instance
                }
            }
            foldAction(players[i], lowestCardInAllHands);
        }
        if (players[i].getPoints() > losingScore) { 
            players[i].setStatus(false);
            numPlayersOut++;
            //Check if game should end immediately
            if (numPlayersOut == players.length - 1){
                playGame = false;
            }
        }
        if(deck.getTotCards() == 0){ // no cards left in deck
            deck.restockDeck();
        }
    }
    

    //Game Actions

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
}