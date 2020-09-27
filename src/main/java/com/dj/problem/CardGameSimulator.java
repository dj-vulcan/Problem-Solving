package com.dj.problem;

public class CardGameSimulator {
    public static void main(String[] args) {
        String inputA = "0O 3X 4O 4X 7X 1O 6X 5O 6O 9X";
        String inputB = "2X 2O 3O 9O 5X 1X 8O 7O 8X 0X";
        simulateCardGame(inputA, inputB);

        inputA = "9X 3O 1X 1O 8O 3X 0O 0X 5O 6X";
        inputB = "6O 2O 5X 9O 8X 2X 4X 4O 7O 7X";
        simulateCardGame(inputA, inputB);

        inputA = "0O 1O 2O 3O 4O 5O 6O 7O 8O 9O";
        inputB = "0X 1X 2X 3X 4X 5X 6X 7X 8X 9X";
        simulateCardGame(inputA, inputB);

        inputA = "1X 1O 3X 3O 5X 5O 7X 7O 9X 9O";
        inputB = "0X 0O 2X 2O 4X 4O 6X 6O 8X 8O";
        simulateCardGame(inputA, inputB);

        inputA = "9O 9X 8O 7X 6O 5X 4O 3X 2O 1X";
        inputB = "0X 1O 2X 3O 4X 5O 6X 7O 8X 0O";
        simulateCardGame(inputA, inputB);
    }

    private static final Player[] players = new Player[2];

    public static void simulateCardGame(String inputA, String inputB) {

        players[0] = new Player();
        players[1] = new Player();

        players[0].setName("A");
        players[1].setName("B");

        Card[] DeckA = stackDeck(inputA);
        Card[] DeckB = stackDeck(inputB);

        players[0].setDeck(DeckA);
        players[1].setDeck(DeckB);

        Player player = players[0];

        player = largestCard(player);
        Card currCard = player.nextCard;
        player.playCard(currCard);

        player = players[1];

        while(true){
            player = nextCard(currCard, player);
            currCard = player.nextCard;

            if (currCard.getNumber() == -1){
                printLoseMessage(player);
                break;
            }
            
            player.playCard(currCard);

            if (player.getName() == "A"){
                player = players[1];
            } else {
                player = players[0];
            }
        }


    }

    private static void printLoseMessage(Player player) {
        System.out.printf("Player %s loses the game!\n", player);

    }

    private static Card[] deleteCard(Card[] Deck, int index){
        int deckSize = Deck.length;
        Card[] newDeck = new Card[deckSize - 1];
        System.arraycopy(Deck, 0, newDeck, 0, index);
        System.arraycopy(Deck, index + 1, newDeck, index, deckSize - index - 1);

        return newDeck;
    }

    private static Player largestCard(Player player){
        int index = 0;
        Card[] Deck = player.getDeck();
        Card largestCard = Deck[0];
        for (int i = 0; i < Deck.length ; i++) {
            if (Deck[i].getNumber() > largestCard.getNumber() || 
                Deck[i].getNumber() == largestCard.getNumber() && Deck[i].getShape() == 'O'){
                largestCard = Deck[i];
                index = i;
            }
        }

        Deck = deleteCard(Deck, index);

        player.setDeck(Deck);
        player.nextCard = largestCard;

        return player;
    }

    private static Card[] stackDeck(String input){
        String[] cardList = input.split(" ");
        Card[] Deck = new Card[10];
        int i = 0;
        for (String card : cardList) {
            Card newCard = new Card();
            String cardNum = ""+card.charAt(0);
            newCard.setNumber(Integer.parseInt(cardNum));
            newCard.setShape(card.charAt(1));
            Deck[i] = newCard;
            i++;
        }

        return Deck;
    }


    private static Player nextCard(Card currCard, Player player){
        int index = 0;
        Card[] Deck = player.getDeck();

        Card largestCard = new Card();
        largestCard.setNumber(-1);

        if (Deck.length <1){
            player.nextCard = largestCard;
            return player;
        }


        for (int i = 0; i < Deck.length ; i++) {
            if (currCard.getNumber() == Deck[i].getNumber()) {
                largestCard = Deck[i];
                Deck = deleteCard(Deck, i);
                player.setDeck(Deck);
                player.nextCard = largestCard;
                return player;
            }
        }

        for (int i = 0; i < Deck.length ; i++) {
            if (currCard.getShape() == Deck[i].getShape()) {
                if (Deck[i].getNumber() >= largestCard.getNumber()){
                    largestCard = Deck[i];
                    index = i;
                }
            }
        }

        Deck = deleteCard(Deck, index);
        player.setDeck(Deck);
        player.nextCard = largestCard;
        return player;

    }
}


class Player {
    private String name;
    private Card[] deck;
    public Card nextCard;

    public void setDeck(Card[] deck) {
        this.deck = deck;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Card[] getDeck() {
        return deck;
    }

    public String getName() {
        return name;
    }

    public void playCard(Card card) {

        System.out.printf("Player %s: %s\n", name, card);
    }

    @Override
    public String toString() {

        return name;
    }
}


class Card {
    private int number;
    private char shape;

    public void setNumber(int number){
        this.number = number;
    }

    public void setShape(char shape) {
        this.shape = shape;
    }

    public int getNumber() {
        return number;
    }

    public char getShape() {
        return shape;
    }

    @Override
    public String toString() {

        return "" + number + shape;
    }
}
