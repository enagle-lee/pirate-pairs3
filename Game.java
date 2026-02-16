import java.util.Scanner;

public class Game {
    int numPlayers;
    int losingScore;
    Player[] players;
    int numPlayersOut = 0;
    Deck deck;
    int typeDeck = 10;

    boolean playGame = true;

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println("How many player are playing? ");
        Scanner scanner = new Scanner(System.in);
        game.numPlayers = scanner.nextInt();
        scanner.close();

        //now can calculate
        game.losingScore = (60 / game.numPlayers) + 1;
        game.players = new Player[game.numPlayers];
        for (int i = 0; i < game.numPlayers ; i++){
            game.players[i] = new Player();
            System.out.println("Player " + i + " Strategy Type: " + game.players[i].getStratLevel());
        }

        game.deck = new Deck(game.typeDeck);
        System.out.println("Losing Score: " + game.losingScore);
        System.out.println("Original Deck: " + game.deck.displayDeck());
        System.out.println("Number of Cards in Deck: " + game.deck.getNumCardsInDeck());
        game.deck.shuffle();
        System.out.println("Shuffled Deck: " + game.deck.displayDeck());
        
        System.out.println("Round 1 ------------------------------------ ");
        game.round1();
        for (int i = 0; i < game.numPlayers; i++) {
            System.out.println("Player " + i + ":" + game.players[i].displayHand());
        }
        
        int counter = 2;
        while(game.playGame){
            System.out.println("Round " + counter + " ------------------------------------ ");
            System.out.println("Deck: " + game.deck.displayDeck());
            System.out.println("Number of Cards in Deck: " + game.deck.getNumCardsInDeck());
            game.round();
            System.out.println("Discard Pile: " + game.deck.displayDiscardPile() + game.deck.getNumCardsInDiscardPile());
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

    // turn method.  calls the strat functions for each player.  checks for pairs after.  if points are added, check if its a valid score

    private void turn(int i){
        int lowestCardInAllHands = getLowestCardInAllHands();
        if (players[i].getStratLevel() == "simple"){
            players[i].stratSimple(lowestCardInAllHands, losingScore);
        } else if (players[i].getStratLevel() == "medium"){
            players[i].stratMedium(lowestCardInAllHands, losingScore);
        } else {
            players[i].stratComplex(lowestCardInAllHands, losingScore, deck.getNumCardsInDeck(), deck.getDiscardPile(), compileOtherHands(i));
        }
        if (players[i].getWillDraw()){
            System.out.print("Player " + i + " drew a card. ");
            drawAction(players[i]);
            if(players[i].hasDouble() > 0){
                System.out.print("But now they have a pair. ");
                foldAction(players[i], players[i].hasDouble());
            }
        }else{ // eats points
            System.out.print("Player " + i + " folded. ");
            //take lowest card from player who has it and add it to discard pile
            for (Player player : players) {
                if (player.removeCard(lowestCardInAllHands)){
                    deck.discard(new int[] {lowestCardInAllHands});
                    break;  // stop after finding first instance
                }
            }
            foldAction(players[i], lowestCardInAllHands);
        }
        System.out.println("Player " + i + ":" + players[i].displayHand() + ". Points: " + players[i].getPoints());

        if (players[i].getPoints() > losingScore) { 
            players[i].setStatus(false);
            numPlayersOut++;
            System.out.println("Player " + i + " is out.");
            //Check if game should end immediately
            if (numPlayersOut == players.length - 1){
                playGame = false;
            }
        }
        if(deck.getNumCardsInDeck() == 0){ // no cards left in deck
            deck.restockDeck();
        }
    }
    

    //Game Actions

    private void drawAction(Player player){
        player.takeCard(deck.drawCard());
    }

    //add folded hand to discard pile
    private void foldAction(Player player, int penaltyCard){
        deck.discard(player.getHand()); //returns updated discard pile
        player.foldResult(penaltyCard); //updates player's points and hand
    }

    private int getLowestCardInAllHands(){
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

    private int[] compileOtherHands(int i){ // compile all hands except for the player passed in
        int totalCards = 0;
        for (int j = 0; j < players.length; j ++){
            if (j != i) {
                totalCards += players[j].getHand().length;
            }
        }
        int[] compiledOtherHands = new int[totalCards];
        int index = 0;
        for (int j = 0; j < players.length; j ++){
            if (j != i) {
                int[] hand = players[j].getHand();
                for (int card : hand){
                    compiledOtherHands[index] = card;
                    index++;
                }
            }
        }
        return compiledOtherHands;
    }
}