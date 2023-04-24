package yahtzee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static yahtzee.Category.*;
import static yahtzee.Die.TOTAL_NUMBER_OF_DICE;

/**
 * @author Yoochul Kim
 */
public class CategoryTests {
    private Die[] dice;

    // The number of 1 is from 1 to 5.
    private int[] onesCorrectResult = {0, 1, 2, 3, 4, 5};
    private int[][] onesTestArray = {{6, 6, 6, 6, 6},
            {1, 6, 6, 6, 6},
            {1, 1, 6, 6, 6},
            {1, 1, 1, 6, 6},
            {1, 1, 1, 1, 6},
            {1, 1, 1, 1, 1}};

    // The number of 2 is from 0 to 5.
    private int[] twosCorrectResult = {0, 2, 4, 6, 8, 10};
    private int[][] twosTestArray = {{6, 6, 6, 6, 6},
            {2, 6, 6, 6, 6},
            {2, 2, 6, 6, 6},
            {2, 2, 2, 6, 6},
            {2, 2, 2, 2, 6},
            {2, 2, 2, 2, 2}};

    // The number of 3 is from 0 to 5.
    private int[] threesCorrectResult = {0, 3, 6, 9, 12, 15};
    private int[][] threesTestArray = {{6, 6, 6, 6, 6},
            {3, 6, 6, 6, 6},
            {3, 3, 6, 6, 6},
            {3, 3, 3, 6, 6},
            {3, 3, 3, 3, 6},
            {3, 3, 3, 3, 3}};

    // The number of 4 is from 0 to 5.
    private int[] foursCorrectResult = {0, 4, 8, 12, 16, 20};
    private int[][] foursTestArray = {{6, 6, 6, 6, 6},
            {4, 6, 6, 6, 6},
            {4, 4, 6, 6, 6},
            {4, 4, 4, 6, 6},
            {4, 4, 4, 4, 6},
            {4, 4, 4, 4, 4}};

    private int[] fivesCorrectResult = {0, 5, 10, 15, 20, 25};
    // The number of 5 is from 0 to 5.
    private int[][] fivesTestArray = {{6, 6, 6, 6, 6},
            {5, 6, 6, 6, 6},
            {5, 5, 6, 6, 6},
            {5, 5, 5, 6, 6},
            {5, 5, 5, 5, 6},
            {5, 5, 5, 5, 5}};

    // The number of 6 is from 0 to 5.
    private int[] sixesCorrectResult = {0, 6, 12, 18, 24, 30};
    private int[][] sixesTestArray = {{1, 1, 1, 1, 1},
            {6, 1, 1, 1, 1},
            {6, 6, 1, 1, 1},
            {6, 6, 6, 1, 1},
            {6, 6, 6, 6, 1},
            {6, 6, 6, 6, 6}};

    @BeforeEach
    public void setup() {
        // Create dice
        dice = new Die[5];
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            dice[i] = new Die();
        }
    }

    @Test
    public void testOnes() {
        for (int i = 0; i < onesTestArray.length; i++) {
            for (int j = 0; j < onesTestArray[i].length; j++) {
                dice[j].setValue(onesTestArray[i][j]);
            }
            Assertions.assertEquals(onesCorrectResult[i], Category.aces(dice));
        }
    }

    @Test
    public void testTwos() {
        for (int i = 0; i < twosTestArray.length; i++) {
            for (int j = 0; j < twosTestArray[i].length; j++) {
                dice[j].setValue(twosTestArray[i][j]);
            }
            Assertions.assertEquals(twosCorrectResult[i], Category.twos(dice));
        }
    }

    @Test
    public void testThrees() {
        for (int i = 0; i < threesTestArray.length; i++) {
            for (int j = 0; j < threesTestArray[i].length; j++) {
                dice[j].setValue(threesTestArray[i][j]);
            }
            Assertions.assertEquals(threesCorrectResult[i], Category.threes(dice));
        }
    }

    @Test
    public void testFours() {
        for (int i = 0; i < foursTestArray.length; i++) {
            for (int j = 0; j < foursTestArray[i].length; j++) {
                dice[j].setValue(foursTestArray[i][j]);
            }
            Assertions.assertEquals(foursCorrectResult[i], Category.fours(dice));
        }
    }

    @Test
    public void testFives() {
        for (int i = 0; i < fivesTestArray.length; i++) {
            for (int j = 0; j < fivesTestArray[i].length; j++) {
                dice[j].setValue(fivesTestArray[i][j]);
            }
            Assertions.assertEquals(fivesCorrectResult[i], fives(dice));
        }
    }

    @Test
    public void testSixes() {
        for (int i = 0; i < sixesTestArray.length; i++) {
            for (int j = 0; j < sixesTestArray[i].length; j++) {
                dice[j].setValue(sixesTestArray[i][j]);
            }
            Assertions.assertEquals(sixesCorrectResult[i], Category.sixes(dice));
        }
    }


    private int[] threeOfAKindCorrectResult = {0, 15, 18, 21};
    private int[][] threeOfAKindTestArray = {{1, 2, 3, 4, 5},
            {1, 1, 1, 6, 6},
            {2, 2, 2, 6, 6},
            {3, 3, 3, 6, 6}};

    private int[] fourOfAKindCorrectResult = {0, 10, 14, 18};
    private int[][] fourOfAKindTestArray = {{1, 2, 3, 4, 5},
            {1, 1, 1, 1, 6},
            {2, 2, 2, 2, 6},
            {3, 3, 3, 3, 6}};

    private int[] fullHouseCorrectResult = {0, 0, 0, FULL_HOUSE_SCORE, FULL_HOUSE_SCORE, FULL_HOUSE_SCORE};
    private int[][] fullHouseTestArray = {{1, 1, 1, 2, 3},
            {1, 1, 1, 1, 2},
            {1, 2, 3, 4, 5},
            {2, 2, 2, 6, 6},
            {3, 3, 3, 6, 6},
            {3, 3, 6, 6, 6}};

    private int[] smallStraightCorrectResult = {0, 0, SMALL_STRAIGHT_SCORE, SMALL_STRAIGHT_SCORE, SMALL_STRAIGHT_SCORE};
    private int[][] smallStraightTestArray = {{1, 2, 4, 5, 6},
            {1, 2, 3, 3, 3},
            {1, 1, 2, 3, 4},
            {2, 3, 3, 4, 5},
            {2, 3, 4, 5, 6}};

    private int[] largeStraightCorrectResult = {0, LARGE_STRAIGHT_SCORE, LARGE_STRAIGHT_SCORE};
    private int[][] largeStraightTestArray = {{1, 2, 3, 4, 4},
            {1, 2, 3, 4, 5},
            {2, 3, 4, 5, 6}};

    private int[] chanceCorrectResult = {5, 10, 15, 20};
    private int[][] chanceTestArray = {{1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3},
            {4, 4, 4, 4, 4}};

    private int[] yahtzeeCorrectResult = {0, YAHTZEE_SCORE, YAHTZEE_SCORE, YAHTZEE_SCORE, YAHTZEE_SCORE};
    private int[][] yahtzeeTestArray = {{1, 2, 3, 4, 5},
            {1, 1, 1, 1, 1},
            {2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3},
            {4, 4, 4, 4, 4}};


    @Test
    public void testThreeOfAKind() {
        for (int i = 0; i < threeOfAKindTestArray.length; i++) {
            for (int j = 0; j < threeOfAKindTestArray[i].length; j++) {
                dice[j].setValue(threeOfAKindTestArray[i][j]);
            }
            Assertions.assertEquals(threeOfAKindCorrectResult[i], Category.threeOfAKind(dice));
        }
    }

    @Test
    public void testFourOfAKind() {
        for (int i = 0; i < fourOfAKindTestArray.length; i++) {
            for (int j = 0; j < fourOfAKindTestArray[i].length; j++) {
                dice[j].setValue(fourOfAKindTestArray[i][j]);
            }
            Assertions.assertEquals(fourOfAKindCorrectResult[i], Category.threeOfAKind(dice));
        }
    }

    @Test
    public void testFullHouse() {
        for (int i = 0; i < fourOfAKindTestArray.length; i++) {
            for (int j = 0; j < fullHouseTestArray[i].length; j++) {
                dice[j].setValue(fullHouseTestArray[i][j]);
            }
            Assertions.assertEquals(fullHouseCorrectResult[i], Category.fullHouse(dice));
        }
    }

    @Test
    public void testSmallStraight() {
        for (int i = 0; i < smallStraightTestArray.length; i++) {
            for (int j = 0; j < smallStraightTestArray[i].length; j++) {
                dice[j].setValue(smallStraightTestArray[i][j]);
            }
            Assertions.assertEquals(smallStraightCorrectResult[i], Category.smallStraight(dice));
        }
    }

    @Test
    public void testLargeStraight() {
        for (int i = 0; i < largeStraightTestArray.length; i++) {
            for (int j = 0; j < largeStraightTestArray[i].length; j++) {
                dice[j].setValue(largeStraightTestArray[i][j]);
            }
            Assertions.assertEquals(largeStraightCorrectResult[i], Category.largeStraight(dice));
        }
    }

    @Test
    public void testChance() {
        for (int i = 0; i < chanceTestArray.length; i++) {
            for (int j = 0; j < chanceTestArray[i].length; j++) {
                dice[j].setValue(chanceTestArray[i][j]);
            }
            Assertions.assertEquals(chanceCorrectResult[i], Category.chance(dice));
        }
    }

    @Test
    public void testYahtzee() {
        for (int i = 0; i < yahtzeeTestArray.length; i++) {
            for (int j = 0; j < yahtzeeTestArray[i].length; j++) {
                dice[j].setValue(yahtzeeTestArray[i][j]);
            }
            Assertions.assertEquals(yahtzeeCorrectResult[i], Category.yahtzee(dice));
        }
    }
}
