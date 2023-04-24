package yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yahtzee.Die;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static yahtzee.Die.AVAILABLE_DICE_VALUES;
import static yahtzee.Die.TOTAL_NUMBER_OF_DICE;

/**
 * @author Yoochul Kim
 */
public class DieTests {
    Die[] dice;

    @BeforeEach
    public void setup() {
        dice = new Die[5];
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            dice[i] = new Die();
        }
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            dice[i].roll();
        }
    }

    @Test
    public void afterRollingDiceItShouldHaveAvailableValue123456() {
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            assertTrue(AVAILABLE_DICE_VALUES.contains(dice[i].getValue()));
        }
    }
}
