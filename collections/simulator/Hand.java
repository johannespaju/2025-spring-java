package collections.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Hand implements Iterable<Card>, Comparable<Hand> {

    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public HandType getHandType() {
        if (isFlush() && isStraight()) {
            return HandType.STRAIGHT_FLUSH;
        } else if (getCountSameCards().equals(List.of(4))) {
            return HandType.FOUR_OF_A_KIND;
        } else if (getCountSameCards().equals(List.of(2, 3))) {
            return HandType.FULL_HOUSE;
        } else if (isFlush()) {
            return HandType.FLUSH;
        } else if (isStraight()) {
            return HandType.STRAIGHT;
        } else if (getCountSameCards().equals(List.of(3))) {
            return HandType.TRIPS;
        } else if (getCountSameCards().equals(List.of(2,2))) {
            return HandType.TWO_PAIRS;
        } else if (getCountSameCards().equals(List.of(2))) {
            return HandType.ONE_PAIR;
        }
        return HandType.HIGH_CARD;
    }

    public boolean isFlush() {
        Collections.sort(cards);
        int counter = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getSuit() == cards.get(i + 1).getSuit()) {
                counter++;
            }
        }
        return counter == 4;
    }

    public boolean isStraight() {
        Collections.sort(cards);
        int counter = 0;

        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getValue().compareTo(cards.get(i + 1).getValue()) == -1) {
                counter++;
            } else if (cards.get(i + 1).getValue() == Card.CardValue.A &&
                    (cards.get(i).getValue() == Card.CardValue.S5 ||
                            cards.get(i).getValue() == Card.CardValue.K)) {
                counter++;
            }
        }
        return counter == 4;
    }

    public List<Integer> getCountSameCards() {
        Collections.sort(cards);

        List<Integer> result = new ArrayList<>();
        int currentCount = 1;

        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getValue().equals(cards.get(i - 1).getValue())) {
                currentCount++;
            } else {
                result.add(currentCount);
                currentCount = 1;
            }
        }
        result.add(currentCount);
        return result;
    }


    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public int compareTo(Hand other) {
        return 0;
    }
}
