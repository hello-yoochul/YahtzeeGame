package yahtzee;

import java.util.*;

/**
 * The Categories (Upper and Lower) are implemented here.
 *
 * @author Yoochul Kim
 */
public class Category {
    public enum Type {
        ACE(0),
        TWOS(1),
        THREES(2),
        FOURS(3),
        FIVES(4),
        SIXES(5),
        THREE_OF_A_KIND(6),
        FOUR_OF_A_KIND(7),
        FULL_HOUSE(8),
        SMALL_STRAIGHT(9),
        LARGE_STRAIGHT(10),
        CHANCE(11),
        YAHTZEE(12);

        public final int value;
        private static Map<Integer, Type> map = new HashMap<>();

        Type(int value) {
            this.value = value;
        }

        static {
            for (Type type : Type.values()) {
                map.put(type.value, type);
            }
        }

        public static Type valueOf(int value) {
            return map.get(value);
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * Total number of categories.
     */
    public static final int NUMBER_OF_CATEGORIES = 13;
    /**
     * Start and end of the categories.
     */
    public static int[] CATEGORY_RANGE = {0, NUMBER_OF_CATEGORIES - 1};
    /**
     * Score for Full House.
     */
    public static final int FULL_HOUSE_SCORE = 25;
    /**
     * Score for Small Straight.
     */
    public static final int SMALL_STRAIGHT_SCORE = 30;
    /**
     * Score for Large Straight.
     */
    public static final int LARGE_STRAIGHT_SCORE = 40;
    /**
     * Score for Yahtzee.
     */
    public static final int YAHTZEE_SCORE = 50;
    /**
     * Last number of the upper section in the categories.
     */
    public static final int LAST_NUMBER_OF_THE_UPPER_SECTION = 5;
    /**
     * When player's upper section goes over 63, this bonus is obtained.
     */
    public static final int UPPER_SECTION_LEAST_SCORE_FOR_BONUS = 63;
    /**
     * When player's upper section goes over 63, this bonus is obtained.
     */
    public static final int UPPER_SECTION_BONUS = 35;

    /**
     * Get the added value of aces.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the added value of aces.
     */
    public static int aces(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < Die.TOTAL_NUMBER_OF_DICE; i++) {
            int iDieValue = dice[i].getValue();
            if (iDieValue == 1)
                addedValue += iDieValue;
        }

        return addedValue;
    }

    /**
     * Get the added value of twos.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the added value of twos.
     */
    public static int twos(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < Die.TOTAL_NUMBER_OF_DICE; i++) {
            int iDieValue = dice[i].getValue();
            if (iDieValue == 2)
                addedValue += iDieValue;
        }

        return addedValue;
    }

    /**
     * Get the added value of threes.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the added value of threes.
     */
    public static int threes(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < Die.TOTAL_NUMBER_OF_DICE; i++) {
            int iDieValue = dice[i].getValue();
            if (iDieValue == 3)
                addedValue += iDieValue;
        }

        return addedValue;
    }

    /**
     * Get the added value of fours.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the added value of fours.
     */
    public static int fours(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < Die.TOTAL_NUMBER_OF_DICE; i++) {
            int iDieValue = dice[i].getValue();
            if (iDieValue == 4)
                addedValue += iDieValue;
        }

        return addedValue;
    }

    /**
     * Get the added value of fives.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the added value of fives.
     */
    public static int fives(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < Die.TOTAL_NUMBER_OF_DICE; i++) {
            int iDieValue = dice[i].getValue();
            if (iDieValue == 5)
                addedValue += iDieValue;
        }

        return addedValue;
    }

    /**
     * Get the added value of sixes.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the added value of sixes.
     */
    public static int sixes(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < Die.TOTAL_NUMBER_OF_DICE; i++) {
            int iDieValue = dice[i].getValue();
            if (iDieValue == 6)
                addedValue += iDieValue;
        }

        return addedValue;
    }

    /**
     * Get the total added value if the combination of dice is three of a kind.
     *
     * @param dice the 5 dice with each value.
     * @return addedValue the total added value of dice.
     */
    public static int threeOfAKind(Die[] dice) {
        int addedValue = 0;
        boolean isDiceThreeOfAKind = false;
        int minimumNumberOfThreeOfAKind = 3;

        for (int i = Die.MIN_DIE_VALUE; i < Die.MAX_DIE_VALUE; i++) {
            int count = 0;
            for (int j = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; j < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; j++) {
                if (dice[j].getValue() == i)
                    count++;

                if (count >= minimumNumberOfThreeOfAKind)
                    isDiceThreeOfAKind = true;
            }
        }

        if (isDiceThreeOfAKind) {
            for (int k = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; k < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; k++) {
                addedValue += dice[k].getValue();
            }
        }

        return addedValue;
    }

    /**
     * Get the total added value if the combination of dice is four of a kind.
     *
     * @param dice the 5 dice with each value.
     * @return result the total added value of dice.
     */
    public static int fourOfAKind(Die[] dice) {
        int result = 0;
        boolean isDiceFourOfAKind = false;
        int minimumNumberOfFourOfAKind = 4;

        for (int i = Die.MIN_DIE_VALUE; i < Die.MAX_DIE_VALUE; i++) {
            int count = 0;
            for (int j = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; j < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; j++) {
                if (dice[j].getValue() == i)
                    count++;

                if (count >= minimumNumberOfFourOfAKind)
                    isDiceFourOfAKind = true;
            }
        }

        if (isDiceFourOfAKind) {
            for (int k = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; k < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; k++) {
                result += dice[k].getValue();
            }
        }

        return result;
    }

    /**
     * Get the Full House score (25) if the combination of dice is Full House.
     *
     * @param dice the 5 dice with each value.
     * @return result if it is full house, return 25, if not return 0.
     */
    public static int fullHouse(Die[] dice) {
        int result = 0;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; i < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; i++) {
            map.put(dice[i].getValue(), map.getOrDefault(dice[i].getValue(), 0) + 1);
        }

        if (map.size() == 2 && map.values().contains(3)) {
            result += FULL_HOUSE_SCORE;
        }

        return result;
    }

    /**
     * Get the Small Straight score (30) if the combination of dice is Small Straight.
     *
     * @param dice the 5 dice with each value.
     * @return result if it is small straight, return 30, if not return 0.
     */
    public static int smallStraight(Die[] dice) {
        int result = 0;

        List<Integer> sortedDiceValueList = new ArrayList<>();
        for (int i = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; i < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; i++) {
            sortedDiceValueList.add(dice[i].getValue());
        }

        Collections.sort(sortedDiceValueList);

//        sortedDiceValueList.forEach((c) -> System.out.println(c));

        // If the sorted dice value goes up by 1, count variable is added by 1.
        // If sequential values in the sorted array are the same, no addition is performed,
        // and if it is not incremented by 1, such as 2->4, the count is reset.
        int count = 0;
        for (int i = 0; i < sortedDiceValueList.size() - 1; i++) {
            if (sortedDiceValueList.get(i) == sortedDiceValueList.get(i + 1)) {
                continue;
            } else if (sortedDiceValueList.get(i) != sortedDiceValueList.get(i + 1) &&
                    sortedDiceValueList.get(i) + 1 == sortedDiceValueList.get(i + 1)) {
                count++;
            } else {
                count = 0;
            }
        }

        if (count >= 3) {
            result += SMALL_STRAIGHT_SCORE;
        }

        return result;
    }

    /**
     * Get the Large Straight score (40) if the combination of dice is Large Straight.
     *
     * @param dice the 5 dice with each value.
     * @return result if it is small straight, return 30, if not return 0.
     */
    public static int largeStraight(Die[] dice) {
        int result = 0;

        List<Integer> sortedDiceValueList = new ArrayList<>();
        for (int i = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; i < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; i++) {
            sortedDiceValueList.add(dice[i].getValue());
        }

        Collections.sort(sortedDiceValueList);

        // sortedDiceValueList.forEach((c) -> System.out.println(c));

        int count = 0;
        for (int i = 0; i < sortedDiceValueList.size() - 1; i++) {
            sortedDiceValueList.get(i);
            if (sortedDiceValueList.get(i) != sortedDiceValueList.get(i + 1) &&
                    sortedDiceValueList.get(i) + 1 == sortedDiceValueList.get(i + 1)) {
                count++;
            }
        }

        if (count >= 4) {
            result += LARGE_STRAIGHT_SCORE;
        }

        return result;
    }

    /**
     * Get the added value of all dice.
     *
     * @param dice the 5 dice with each value.
     * @return result the added value of all dice.
     */
    public static int chance(Die[] dice) {
        int addedValue = 0;

        for (int i = 0; i < 5; i++) {
            addedValue += dice[i].getValue();
        }

        return addedValue;
    }

    /**
     * Get the Yahtzee score (50) if the combination of dice is Yahtzee.
     *
     * @param dice the 5 dice with each value.
     * @return result if it is yahtzee, return 50, if not return 0.
     */
    public static int yahtzee(Die[] dice) {
        int result = 0;

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int i = Die.FIRST_DIE_NUMBER_IN_THE_ARRAY; i < Die.LAST_DIE_NUMBER_IN_THE_ARRAY; i++) {
            map.put(dice[i].getValue(), map.getOrDefault(dice[i].getValue(), 0) + 1);
        }

        if (map.size() == 1) {
            result += YAHTZEE_SCORE;
        }

        return result;
    }
}








