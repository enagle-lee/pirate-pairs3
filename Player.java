import java.util.Arrays;

public class Player {
    private int points = 0;
    private int[] hand = new int[0];
    private boolean willDraw;
    private String stratLevel;
    private boolean inGame = true;

    // Constructor

    public Player(){
        int i = (int) (Math.random() * 3) + 1;
        if (i == 1){
            stratLevel = "simple";
        } else if (i == 2) {
            stratLevel = "medium";
        } else {
            stratLevel = "complex";
        }
    }

    // In-game Action Methods

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

    public boolean removeCard(int cardValue){
        //find card
        for (int i = 0; i < hand.length; i++){
            if (hand[i] == cardValue){
                // create new hand without that card
                int[] newHand = new int[hand.length - 1];
                for (int j = 0; j < i; j++){
                    newHand[j] = hand[j];
                }
                for (int j = i; j < newHand.length; j++){
                    newHand[j] = hand[j + 1];
                }
                hand = newHand;
                return true;  // Card found and removed
            }
        }
        return false;
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
       
    // Strategy Methods -- which to exectute in Game class is randomly assigned to each player in the constructor
    // Changes the willDraw boolean.  Game class then executes a broad action based on that boolean 

    public void stratSimple(int lowestCardInAllHands, int losingScore){
        if(points + lowestCardInAllHands >= losingScore) { // If folding would eliminate me, I should draw
            willDraw = true;
        }else if (hand.length >3) {
            willDraw = false;
        } else {
            willDraw = true;
        }
    }

    public void stratMedium(int lowestCardInAllHands, int losingScore){        
        // Must draw if no cards
        if (hand.length == 0) {
            willDraw = true;
        } else if(points + lowestCardInAllHands >= losingScore) { // If folding would eliminate me, I should draw 
            willDraw = true;
        } else {
            int myLowest = getLowestCardInHand();
            int myHighest = getHighestCardInHand();

            if (hand.length == 1) {
                // If I have the lowest card, nothing to lose by drawing
                if (myLowest == lowestCardInAllHands) {
                    willDraw = true;
                }
                // If my card is much worse than the lowest, fold and steal it
                else if (myLowest - lowestCardInAllHands >= 5 && myLowest > 8) {
                    willDraw = false;
                }
                else {
                    willDraw = true;
                }
            } else if (hand.length == 2) {
                // Both cards are the same and lowest in all hands, might as well draw
                if (myHighest == myLowest && myLowest == lowestCardInAllHands) {
                    willDraw = true;
                } else if (myHighest >= 8 && myLowest >= 8) {    // Both cards are high (risky hand)
                    if (lowestCardInAllHands <= 5) {     // If the lowest card is much better, fold and steal it
                        willDraw = false;
                    } else {
                        willDraw = true;
                    }
                } else {
                    willDraw = true;
                }
            } else { // hand size of 3 or more
                if(lowestCardInAllHands >= 8){  //if the lowest card in play is a high card --> might as well draw
                    willDraw = true;
                }else{
                    willDraw = false;
                }
            }
        }
    }

    public void stratComplex(int lowestCardInAllHands, int losingScore, int totalCards, int[] discardPile, int[] otherHands){
        if (hand.length == 0) { // Must draw if no cards
            willDraw = true;
        } else if(points + lowestCardInAllHands >= losingScore) { // If folding would eliminate me, I should draw 
            willDraw = true;
        } else {
            int myLowest = getLowestCardInHand(); //player's lowest card
            int myHighest = getHighestCardInHand(); //player's highest card
            if (hand.length == 1 && myLowest == lowestCardInAllHands) {  // If I have the lowest card, nothing to lose by drawing
                    willDraw = true;
            } else if (myHighest - lowestCardInAllHands <= 3){ //ie my cards aren't that much different than the points I'd be getting by folding, might as well draw
                willDraw = true;
            } else {
                double threshold; // Adjust threshold based on game state
                int pointsUntilLoss = losingScore - points;
                if (pointsUntilLoss <= (int) (losingScore * 0.75)) {
                    threshold = 0.1;  // most conservative when close to losing
                } else if (pointsUntilLoss <= (int) (losingScore * 0.66)) {
                    threshold = 0.15;  // more conservative
                } else {
                    threshold = 0.2;  // standard risk tolerance
                }
                double totalRisk = 0;
                for (int card : hand) { //calculate and add the risk for each card in a player's hand
                    totalRisk += calcPairRisk(card, totalCards, discardPile, otherHands);
                }
                if (totalRisk > threshold){
                    willDraw = false;
                } else {
                    willDraw = true;                    
                }
                System.out.println("Calculated risk: " + totalRisk + " -->");
            }
        }
    }

    private double calcPairRisk(int riskyCard, int totalCardsInDeck, int[] discardPile, int[] otherHands){
        int counter = 0;
        for (int card : discardPile){
            if (riskyCard == card){
                counter++;
            }
        }
        for (int card : otherHands){
            if (riskyCard == card){
                counter++;
            }
        }
        //card value == # of that card in existence
        double risk = (double) (riskyCard - counter) / totalCardsInDeck;
        return risk;
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

    public int getLowestCardInHand(){
        int lowest = hand[0];
        for(int card : hand){
            if (card < lowest) {
                lowest = card;
            }
        }
        return lowest;
    }
    public int getHighestCardInHand(){
        int highest = hand[0];
        for(int card : hand){
            if (card > highest) {
                highest = card;
            }
        }
        return highest;
    }

    public String getStratLevel(){
        return stratLevel;
    }
    

    public boolean getWillDraw(){
        return willDraw;
    }

    public boolean getStatus(){
        return inGame;
    }

    //Setters

    public void setStratLevel(String stratLevel){
        this.stratLevel = stratLevel;
    }

    public void setStatus(boolean inGame) {
        this.inGame = inGame;
    }
}
