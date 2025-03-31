import java.util.Scanner;
public class lab6 {

    public static LinkedList initialize_deck() {

        LinkedList deck = new LinkedList();

        // populate linked list with a single deck of cards
        for (Card.suites s : Card.suites.values()) {
            for(Card.ranks r : Card.ranks.values()) {
                if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                    Card newCard = new Card(s, r);
                    //newCard.print_card();
                    deck.add_at_tail(newCard);
                }
            }
        }

        return deck;
    }

    private static void play_blind_mans_bluff(LinkedList player1, LinkedList computer, LinkedList deck) {
        System.out.println("\nStarting Blind mans Bluff \n");
        Scanner scanner = new Scanner(System.in);
        int losses = 0, wins = 0;

        for (int round = 0; round < 5; round++) {
            Card playerCard = player1.remove_from_head();
            Card computerCard = computer.remove_from_head();

            System.out.println("Opponent's card: " + computerCard.suit + " " + computerCard.rank);
            System.out.print("Do you think your card is higher or lower? (h/l): ");
            String guess = scanner.next();

            boolean playerWins = compareCards(playerCard, computerCard);
            System.out.println("Your card was: " + playerCard.suit + " " + playerCard.rank);

            if ((guess.equals("h") && playerWins) || (guess.equals("l") && !playerWins)) {
                System.out.println("You win this round!");
                wins++;
                losses = 0;
            } else {
                System.out.println("You lost this round.");
                losses++;
            }

            if (losses == 3) {
                System.out.println("You lost 3 times in a row. Rage quitting...");
                rage_quit(deck, player1, computer);
                losses = 0;
            }
        }
        System.out.println("Game over! Wins: " + wins + ", Losses: " + (5 - wins));
    }
    // Compare two cards
    private static boolean compareCards(Card playerCard, Card computerCard) {
        if (playerCard.rank.ordinal() > computerCard.rank.ordinal()) return true;
        if (playerCard.rank.ordinal() < computerCard.rank.ordinal()) return false;
        return playerCard.suit.ordinal() > computerCard.suit.ordinal();
    }
    private static void rage_quit(LinkedList deck, LinkedList player1, LinkedList computer) {
        while (deck.size < 52) {
            for (Card.suites s : Card.suites.values()) {
                for (Card.ranks r : Card.ranks.values()) {
                    if (r != Card.ranks.NULL && s != Card.suites.NULL) {
                        deck.add_at_tail(new Card(s, r));
                    }
                }
            }
        }
        deck.shuffle(512);

        for (int i = 0; i < 5; i++) {
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }
    }


    public static void main(String[] args) {

        // create a deck (in order)
        LinkedList deck = initialize_deck();
        deck.print();
        deck.sanity_check(); // because we can all use one

        // shuffle the deck (random order)
        deck.shuffle(512);
        deck.print();
        deck.sanity_check(); // because we can all use one

        // cards for player 1 (hand)
        LinkedList player1 = new LinkedList();
        // cards for player 2 (hand)
        LinkedList computer = new LinkedList();

        int num_cards_dealt = 5;
        for (int i = 0; i < num_cards_dealt; i++) {
            // player removes a card from the deck and adds to their hand
            player1.add_at_tail(deck.remove_from_head());
            computer.add_at_tail(deck.remove_from_head());
        }

        // let the games begin!
        play_blind_mans_bluff(player1, computer, deck);
    }
}
