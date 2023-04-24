package yahtzee;


import java.util.List;

/**
 * The dice features are implemented here.
 *
 * @author Yoochul Kim
 */
public class Die {
    /**
     * Available dice values.
     */
    public static final List<Integer> AVAILABLE_DICE_VALUES = List.of(1, 2, 3, 4, 5, 6);
    /**
     * A fixed number of dice.
     */
    public static final int TOTAL_NUMBER_OF_DICE = 5;
    /**
     * Minimum value of Die.
     */
    public static final int MIN_DIE_VALUE = 1;
    /**
     * Maximum value of Die.
     */
    public static final int MAX_DIE_VALUE = 6;
    /**
     * First dice array number.
     */
    public static final int FIRST_DIE_NUMBER_IN_THE_ARRAY = 0;
    /**
     * Second dice array number.
     */
    public static final int SECOND_DIE_NUMBER_IN_THE_ARRAY = 1;
    /**
     * Thirds dice array number.
     */
    public static final int THIRD_DIE_NUMBER_IN_THE_ARRAY = 2;
    /**
     * Fourth dice array number.
     */
    public static final int FOURTH_DIE_NUMBER_IN_THE_ARRAY = 3;
    /**
     * Fifth dice array number.
     */
    public static final int FIFTH_DIE_NUMBER_IN_THE_ARRAY = 4;
    /**
     * Last(sixth) dice array number.
     */
    public static final int LAST_DIE_NUMBER_IN_THE_ARRAY = 5;
    /**
     * Last(sixth) dice array number.
     */
    public static final int NUMBER_OF_AVAILABLE_RETRY_ATTEMPTS = 2;

    // the die value.
    private int value;

    /**
     * Roll the die to create an integer value between
     * 1 and 6, and store it.
     */
    public void roll() {
        value = (int) (Math.random() * 6 + 1);
    }

    /**
     * Get the dice value.
     *
     * @return value the die value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Set a value in the global variable.
     * It is used during test.
     *
     * @param value initialize a die with the specified value.
     */
    public void setValue(int value) {
        this.value = value;
    }
}
