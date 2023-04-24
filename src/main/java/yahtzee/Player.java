package yahtzee;

import java.util.*;

import static yahtzee.Category.*;

/**
 * Player is implemented here.
 *
 * @author Yoochul Kim
 */
public class Player {
    // 0: Ones, 1: Twos, 2: Threes, 3: Fours, 4: Fives, 5: Sixes, 6: Three of a kind,
    // 7: Four of a kind, 8: Full House, 9: Small straight, 10: Large straight, 11: Chance, 12: YAHTZEE
    private int[] categoriesScores;
    private boolean[] areCategoriesFilled;

    private String name;

    private boolean hasWon;

    // After calculation of the scores in categories in the end of game, this value will be returned.
    private int totalScore;

    public Player() {
    }

    /**
     * Initialize a player.
     *
     * @param name name of player.
     */
    public Player(String name) {
        this.name = name;

        categoriesScores = new int[NUMBER_OF_CATEGORIES];
        areCategoriesFilled = new boolean[NUMBER_OF_CATEGORIES];

        Arrays.fill(categoriesScores, 0);
        Arrays.fill(areCategoriesFilled, false);

        totalScore = 0;

        hasWon = false;
    }

    /**
     * Set a name of player.
     *
     * @param name a name of player to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get a name of player.
     *
     * @return name a name of player.
     */
    public String getName() {
        return name;
    }

    /**
     * Get a total score of the categories.
     *
     * @return totalScore total score of the categories.
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Set a total score of the categories.
     */
    public void setTotalScore(int totalScore){
        this.totalScore = totalScore;
    }

    /**
     * Used in YahtzeeModelImplTests to quickly determine winners.
     *
     * @param mockTotalScore a mock total score.
     */
    public void setTotalScoreForUnitTest(int mockTotalScore) {
        totalScore = mockTotalScore;
    }

    /**
     * Check if one has won the game.
     *
     * @return hasWon true if one has won the game.
     */
    public boolean hasWon() {
        return hasWon;
    }

    /**
     * After rolling dice, player fill a certain category,
     * which are allowed to do.
     *
     * @param categoryNumber the specified category number.
     * @param score          the specified category score.
     */
    public void fillCategory(int categoryNumber, int score) {
        if (canChooseThisCategory(categoryNumber)) {
            categoriesScores[categoryNumber] = score;
            areCategoriesFilled[categoryNumber] = true;
        } else {
            throw new IllegalArgumentException("The Category has been already filled");
        }
    }

    /**
     * Check if the category can be filled. Once players fill certain
     * category, they will not be allowed to choose the same category again.
     *
     * @param categoryNumber the specified category number.
     * @return isCategoryAvailable true if the category can be chosen.
     */
    public boolean canChooseThisCategory(int categoryNumber) {
        return !areCategoriesFilled[categoryNumber];
    }

    /**
     * Get the available categories.
     *
     * @return availableCategoriesArray
     */
    public int[] getAvailableCategories() {
        List<Integer> availableCategoriesList = new ArrayList<>();

        for (int i = 0; i < areCategoriesFilled.length; i++) {
            if (!areCategoriesFilled[i]) {
                availableCategoriesList.add(i);
            }
        }

        int[] availableCategoriesArray = new int[availableCategoriesList.size()];

        for (int i = 0; i < availableCategoriesList.size(); i++) {
            availableCategoriesArray[i] = availableCategoriesList.get(i);
        }

        return availableCategoriesArray;
    }

    /**
     * Get scores of all available combinations.
     * If only 0 and 1 in the areCategoriesFilled array are filled,
     * and the value of dice are 2, 2, 2, 3, and 3,
     * [ 2=6, 3=6, 4=0, ...] would be returned.
     *
     * @param dice to be used for finding combinations.
     * @return map of scores of all available combinations.
     */
    public Map<Integer, Integer> getScoresOfAvailableCombinations(Die[] dice) {
        Map<Integer, Integer> availableCombinationsMap = new HashMap<>();

        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            if (!areCategoriesFilled[i]) {
                availableCombinationsMap.put(i, calculateCategoryScore(i, dice));
            }
        }

        return availableCombinationsMap;
    }

    /**
     * Calculate the score of a certain category.
     *
     * @param categoryNumber the certain number of category. e.g, Aces = 1, Two = 2, ... .
     * @param dice           to be used for a calculation.
     * @return the score of a certain category.
     */
    public int calculateCategoryScore(int categoryNumber, Die[] dice) {
        if (categoryNumber < CATEGORY_RANGE[0] || CATEGORY_RANGE[1] < categoryNumber) {
            throw new IllegalArgumentException("wrong categories selection");
        }

        int result = 0;

        switch (Type.valueOf(categoryNumber)) {
            case ACE:
                result = Category.aces(dice);
                break;
            case TWOS:
                result = Category.twos(dice);
                break;
            case THREES:
                result = Category.threes(dice);
                break;
            case FOURS:
                result = Category.fours(dice);
                break;
            case FIVES:
                result = Category.fives(dice);
                break;
            case SIXES:
                result = Category.sixes(dice);
                break;
            case THREE_OF_A_KIND:
                result = Category.threeOfAKind(dice);
                break;
            case FOUR_OF_A_KIND:
                result = Category.fourOfAKind(dice);
                break;
            case FULL_HOUSE:
                result = Category.fullHouse(dice);
                break;
            case SMALL_STRAIGHT:
                result = Category.smallStraight(dice);
                break;
            case LARGE_STRAIGHT:
                result = Category.largeStraight(dice);
                break;
            case CHANCE:
                result = Category.chance(dice);
                break;
            case YAHTZEE:
                result = Category.yahtzee(dice);
                break;
        }

        return result;
    }

    /**
     * Get scores in the categories.
     *
     * @return categoriesScore int list of the categories' scores.
     */
    public int[] getCategoriesScores() {
        return categoriesScores;
    }

    /**
     * Check if the categories are filled.
     *
     * @return areCategoriesFilled boolean list of the categories.
     */
    public boolean[] areCategoriesFilled() {
        return areCategoriesFilled;
    }

    /**
     * Calculate total score in the categories, if one succeed in
     * Yahtzee and getting over 63 score in upper section, they
     * would have extra bonus.
     */
    public void calculateTotalScore() {
        for (boolean isCategoryFilled : areCategoriesFilled) {
            if (!isCategoryFilled)
                throw new IllegalStateException("The game has not finished, please fill all the categories");
        }

        int sum = 0;
        int upperSectionTotalScore = 0;

        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            if (i <= LAST_NUMBER_OF_THE_UPPER_SECTION) {
                upperSectionTotalScore += categoriesScores[i];
            }
            sum += categoriesScores[i];
        }

        if (hasUpperSectionGoneOver63(upperSectionTotalScore)) increaseTotalScoreBy(UPPER_SECTION_BONUS);
        increaseTotalScoreBy(sum);
    }

    /**
     * Check if the upper section goes over 63.
     *
     * @param upperSectionTotalScore total score of the upper section.
     * @return HasUpperSectionGoneOver63 true if the upper section goes over 63.
     */
    private boolean hasUpperSectionGoneOver63(int upperSectionTotalScore) {
        return upperSectionTotalScore >= UPPER_SECTION_LEAST_SCORE_FOR_BONUS;
    }

    private void increaseTotalScoreBy(int plusScore){
        totalScore += plusScore;
    }
}