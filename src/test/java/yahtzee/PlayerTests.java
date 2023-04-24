package yahtzee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static yahtzee.Category.NUMBER_OF_CATEGORIES;

/**
 * @author Yoochul Kim
 */
public class PlayerTests {
    private Player mockPlayer1;
    private Player mockPlayer2;
    private Player mockPlayer3;

    private List<String> anyNames;

    @BeforeEach
    public void setup() {
        anyNames = List.of("anyName1", "anyName2", "anyName3");
        mockPlayer1 = new Player(anyNames.get(0));
        mockPlayer2 = new Player(anyNames.get(1));
        mockPlayer3 = new Player(anyNames.get(2));
    }

    @Test
    public void allVariablesShouldBeInitialized() {
        assertEquals(anyNames.get(0), mockPlayer1.getName());
        assertEquals(anyNames.get(1), mockPlayer2.getName());
        assertEquals(anyNames.get(2), mockPlayer3.getName());

        assertEquals(0, mockPlayer1.getTotalScore());
        assertEquals(0, mockPlayer2.getTotalScore());
        assertEquals(0, mockPlayer3.getTotalScore());

        assertFalse(mockPlayer1.hasWon());
        assertFalse(mockPlayer2.hasWon());
        assertFalse(mockPlayer3.hasWon());

        int[] categoriesScore1 = mockPlayer1.getCategoriesScores();
        boolean[] areCategoriesFilled1 = mockPlayer1.areCategoriesFilled();
        for (int i = 0; i < categoriesScore1.length; i++) {
            assertEquals(0, categoriesScore1[i]);
            assertSame(false, areCategoriesFilled1[i]);
        }

        int[] categoriesScore2 = mockPlayer2.getCategoriesScores();
        boolean[] areCategoriesFilled2 = mockPlayer2.areCategoriesFilled();
        for (int i = 0; i < categoriesScore2.length; i++) {
            assertEquals(0, categoriesScore2[i]);
            assertSame(false, areCategoriesFilled2[i]);
        }

        int[] categoriesScore3 = mockPlayer3.getCategoriesScores();
        boolean[] areCategoriesFilled3 = mockPlayer3.areCategoriesFilled();
        for (int i = 0; i < categoriesScore3.length; i++) {
            assertEquals(0, categoriesScore3[i]);
            assertSame(false, areCategoriesFilled3[i]);
        }
    }

    @Test
    public void categoriesShouldBeSetOnce() {
        int anyCategoryNumber = 0;
        int anyScore = 10;

        // 1st time
        mockPlayer1.fillCategory(anyCategoryNumber, anyScore);

        // 2nd time in the same position
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mockPlayer1.fillCategory(anyCategoryNumber, anyScore);
        });
    }

    @Test
    public void availableCategoriesShouldBeReturnedCorrectly() {
        // None of categories is selected.
        assertEquals(NUMBER_OF_CATEGORIES, mockPlayer1.getAvailableCategories().length);
        assertEquals(NUMBER_OF_CATEGORIES, mockPlayer2.getAvailableCategories().length);
        assertEquals(NUMBER_OF_CATEGORIES, mockPlayer3.getAvailableCategories().length);

        int anyScore = 10;

        // One category of first mockPlayer is filled, and it checks that
        // the category is not included in the available categories and that the number of available category has decreased.
        int anyCategoryNumber1 = Category.Type.ACE.getValue();
        mockPlayer1.fillCategory(anyCategoryNumber1, anyScore);
        Arrays.stream(mockPlayer1.getAvailableCategories()).forEach(categoryNumber -> {
            assertTrue(categoryNumber != Category.Type.ACE.getValue());
        });
        assertEquals(NUMBER_OF_CATEGORIES - 1, mockPlayer1.getAvailableCategories().length);

        // two categories are filled and checking is same as above
        int anyCategoryNumber2_1 = Category.Type.TWOS.getValue();
        int anyCategoryNumber2_2 = Category.Type.THREES.getValue();
        mockPlayer2.fillCategory(anyCategoryNumber2_1, anyScore);
        mockPlayer2.fillCategory(anyCategoryNumber2_2, anyScore);
        Arrays.stream(mockPlayer2.getAvailableCategories()).forEach(categoryNumber -> {
            assertTrue(categoryNumber != Category.Type.TWOS.getValue());
            assertTrue(categoryNumber != Category.Type.THREES.getValue());
        });
        assertEquals(NUMBER_OF_CATEGORIES - 2, mockPlayer2.getAvailableCategories().length);


        // three categories are filled and checking is same as above
        int anyCategoryNumber3_1 = Category.Type.FOURS.getValue();
        int anyCategoryNumber3_2 = Category.Type.FIVES.getValue();
        int anyCategoryNumber3_3 = Category.Type.SIXES.getValue();
        mockPlayer3.fillCategory(anyCategoryNumber3_1, anyScore);
        mockPlayer3.fillCategory(anyCategoryNumber3_2, anyScore);
        mockPlayer3.fillCategory(anyCategoryNumber3_3, anyScore);
        Arrays.stream(mockPlayer3.getAvailableCategories()).forEach(categoryNumber -> {
            assertTrue(categoryNumber != Category.Type.FOURS.getValue());
            assertTrue(categoryNumber != Category.Type.FIVES.getValue());
            assertTrue(categoryNumber != Category.Type.SIXES.getValue());
        });
        assertEquals(NUMBER_OF_CATEGORIES - 3, mockPlayer3.getAvailableCategories().length);
    }

    @Test
    public void fillingAllCategoriesShouldBePrecededBeforeCalculationOfTotalScore() {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            mockPlayer1.calculateTotalScore();
        });
    }

    @Test
    public void calculationOfTotalScoreInTheEndOfGameShouldBeCorrect() {
        // player 1: all 0 in categories
        int[] allZeroScore = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            mockPlayer1.fillCategory(i, allZeroScore[i]);
        }
        mockPlayer1.calculateTotalScore();
        assertEquals(0, mockPlayer1.getTotalScore());

        // player 2: assumed that he would have sum of the upper section more than 63
        int[] scoresWhichSumOfUpperSectionGoesOver63 = new int[]{10, 10, 10, 10, 10, 13, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            mockPlayer2.fillCategory(i, scoresWhichSumOfUpperSectionGoesOver63[i]);
        }
        mockPlayer2.calculateTotalScore();
        assertEquals(98, mockPlayer2.getTotalScore());
    }
}
