import java.util.Arrays;

public class Deck {
    private static int highestCard;
    private static int totCards = 0;
    private static int[] deck;

    public Deck(int highestCard){
        this.highestCard = highestCard;
        for (int i = 1; i <= highestCard; i++) {
            totCards += i;
        }
        // make an array for cards yet to be drawn
        deck = new int[totCards];
        int tempLoc = 0;
        for (int i = 1; i <= highestCard; i++){
            for (int j = 0; j < i; j++){
            deck[tempLoc] = i;
            tempLoc ++;
            }
        }
    }

    // getters

    public static int getHighestCard(){
        return highestCard;
    }
    public static int getTotCards(){
        return totCards;
    }
    public void getDeck(){
        System.out.print(Arrays.toString(deck));
        }
    }

    // array for discarded cards

    // remove card function

    // shuffle function
    public void shuffle(){
        int a = (int) (Math.random() * totCards) + 1;
        int b = (int) (Math.random() * totCards) + 1;
        if (a != b) {
            int tempValue = deck[a];
            deck[a] = deck [b];
            deck[b] = tempValue;
        }
    }



