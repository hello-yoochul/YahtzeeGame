package yahtzee;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static yahtzee.Category.*;
import static yahtzee.Die.*;
import static yahtzee.YahtzeeGameImpl.MAXIMUM_POSSIBLE_SCORE_ATTAINABLE_IN_YAHTZEE_GAME;
import static yahtzee.YahtzeeGameImpl.TOTAL_NUMBER_OF_ROUNDS;

/**
 * @author Yoochul Kim
 */
public class YahtzeeGameImplTests {
    private String[] mockPlayerNames;
    private YahtzeeGameImpl mockGame;
    private Player[] mockPlayers;
    private Die[] mockDice;
    private int totalNumberOfPlayers;

    @BeforeEach
    public void setup() {
        mockPlayerNames = new String[]{"anyName1", "anyName2", "anyName3"};
        mockGame = new YahtzeeGameImpl();
        mockGame.makeGame(mockPlayerNames);

        // Get players in the game.
        totalNumberOfPlayers = mockGame.getTotalNumberOfPlayers();
        mockPlayers = new Player[totalNumberOfPlayers];
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            mockPlayers[i] = new Player();
        }
        mockPlayers = mockGame.getPlayers();

        // Get five dice in the game.
        mockDice = new Die[TOTAL_NUMBER_OF_DICE];
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            mockDice[i] = new Die();
        }
        mockDice = mockGame.getDice();
    }

    @Test
    public void allVariablesShouldBeInitializedAtTheBeginning() {
        // a number of players should be same as the length of playerNames to be sent.
        assertEquals(mockPlayerNames.length, mockGame.getTotalNumberOfPlayers());

        // the names to be sent should be set in Player objects
        for (int i = 0; i < mockPlayerNames.length; i++) {
            assertEquals(mockPlayerNames[i], mockGame.getPlayers()[i].getName());
        }

        // 5 dice should be ready to use.
        assertEquals(TOTAL_NUMBER_OF_DICE, mockGame.getDice().length);

        // turn starts at 0 and increased by 1 till 13.
        assertEquals(0, mockGame.getCurrentRound());

        // Each player will have three retry attempts of rolling dice.
        assertEquals(0, mockGame.getDiceRetryCount());

        // The player number should start at 0 as the player array in the game start at zero.
        assertEquals(0, mockGame.getCurrentPlayerNumber());
    }

    @Test
    public void rollingTheDiceShouldYieldCorrectValues() {
        // 1. when player wants to roll all five dice.
        mockGame.rollDice();
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            assertTrue(AVAILABLE_DICE_VALUES.contains(mockDice[i].getValue()));
        }

        // 2. When choosing dice, players can only choose among 5 dice.
        // i.e. out range such as 8 are expected to throw exceptions.
        int[] diceNumberOutOfRange = {1, 10, 11};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mockGame.rollCertainDice(diceNumberOutOfRange);
        });

        // 3. when player wants to roll certain dice.
        // Test 10 times in case it gets hit by chance.
        Die[] previousDice = mockDice.clone();
        for (int i = 0; i < 10; i++) {
            int[] mockKeptDice = {Die.SECOND_DIE_NUMBER_IN_THE_ARRAY, Die.FOURTH_DIE_NUMBER_IN_THE_ARRAY};
            // roll the certain dice.
            mockGame.rollCertainDice(mockKeptDice);

            Die[] currentDice = mockGame.getDice();

            // Arrays.stream(currentDice).forEach(e -> System.out.print(e.getValue() + " "));
            // System.out.println();

            for (int j = 0; j < TOTAL_NUMBER_OF_DICE; j++) {
                // The kept dice must hold the same value.
                if (j == Die.SECOND_DIE_NUMBER_IN_THE_ARRAY || j == Die.FOURTH_DIE_NUMBER_IN_THE_ARRAY) {
                    assertEquals(previousDice[j].getValue(), currentDice[j].getValue());
                }
            }
        }
    }

    @Test
    public void winnerShouldBeReturnedCorrectly() {
        // 100 110 120 -> rank: 3 2 1
        int[] mockScore1 = {100, 110, 120};
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            mockGame.getPlayers()[i].setTotalScore(mockScore1[i]);
        }
        assertEquals(1, mockGame.getWinners().length);

        // 100 120 120 -> rank: 2 1 1
        int[] mockScore2 = {100, 120, 120};
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            mockGame.getPlayers()[i].setTotalScore(mockScore2[i]);
        }
        assertEquals(2, mockGame.getWinners().length);

        // 100 100 100 -> rank: 1 1 1
        int[] mockScore3 = {100, 100, 100};
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            mockGame.getPlayers()[i].setTotalScore(mockScore3[i]);
        }
        assertEquals(3, mockGame.getWinners().length);
    }

    @Test
    public void calculationOfPlayersScoreShouldBeWorking() {
        // the categories have not been filled so it throws IllegalArgumentException
        Assertions.assertThrows(IllegalStateException.class, () -> {
            mockGame.calculateScores();
        });

        int[] mockScores = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
        int sumOfMockScores = 130;

        for (int i = 0; i < totalNumberOfPlayers; i++) {
            for (int j = 0; j < NUMBER_OF_CATEGORIES; j++) {
                mockPlayers[i].fillCategory(j, mockScores[i]);
            }
        }

        mockGame.calculateScores();

        for (int i = 0; i < totalNumberOfPlayers; i++) {
            assertEquals(sumOfMockScores, mockPlayers[i].getTotalScore());
        }
    }

    @Test
    public void turnShouldBeIterative() {
        // In this test, there are three players (anyName1, anyName2, anyName3).
        assertEquals(0, mockGame.getCurrentPlayerNumber());
        mockGame.passTurnToNextPlayer();
        assertEquals(1, mockGame.getCurrentPlayerNumber());
        mockGame.passTurnToNextPlayer();
        assertEquals(2, mockGame.getCurrentPlayerNumber());
        mockGame.passTurnToNextPlayer();
        assertEquals(0, mockGame.getCurrentPlayerNumber());
    }

    @Test
    public void availableCategoriesShouldBeCorrectly() {
        int anyScore = 10;

        // assumed that it is 1st round, and everyone chooses TWOS.
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            assertEquals(NUMBER_OF_CATEGORIES, mockGame.getAvailableCategoriesOfCurrentPlayer().length);
            assertEquals(i, mockGame.getCurrentPlayerNumber());
            mockGame.fillCategory(Type.TWOS.getValue(), anyScore);
            mockGame.passTurnToNextPlayer();
        }
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            int[] availableCategories = mockPlayers[i].getAvailableCategories();
            Arrays.stream(availableCategories).forEach(categoryNumber -> {
                assertTrue(categoryNumber != Type.TWOS.getValue());
            });
            assertEquals(NUMBER_OF_CATEGORIES - 1, availableCategories.length);

            /*Arrays.stream(availableCategories).forEach(e -> {
                System.out.print(e + " ");
            });
            System.out.println();*/
        }

        // assumed that it is 2nd round, and everyone chooses SIXES.
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            assertEquals(NUMBER_OF_CATEGORIES - 1, mockGame.getAvailableCategoriesOfCurrentPlayer().length);
            assertEquals(i, mockGame.getCurrentPlayerNumber());
            mockGame.fillCategory(Type.SIXES.getValue(), anyScore);
            mockGame.passTurnToNextPlayer();
        }
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            int[] availableCategories = mockPlayers[i].getAvailableCategories();
            Arrays.stream(availableCategories).forEach(categoryNumber -> {
                assertTrue(categoryNumber != Type.SIXES.getValue());
            });
            assertEquals(NUMBER_OF_CATEGORIES - 2, availableCategories.length);

            /*Arrays.stream(availableCategories).forEach(e -> {
                System.out.print(e + " ");
            });
            System.out.println();*/
        }
    }

    @Test
    public void availableCombinationsOfTheDiceShouldBeReturned() {
        Player firstPlayer = mockPlayers[0];
        Map<Integer, Integer> availableCombinationsMap =
                mockGame.getScoresOfAvailableCombinations(firstPlayer, mockDice);

        // 1. None of the player's categories are filled currently. Therefore, all categories should be available.
        assertEquals(NUMBER_OF_CATEGORIES, availableCombinationsMap.size());

        int anyScore = 10;
        firstPlayer.fillCategory(Category.Type.ACE.getValue(), anyScore);
        firstPlayer.fillCategory(Type.TWOS.getValue(), anyScore);

        availableCombinationsMap =
                mockGame.getScoresOfAvailableCombinations(firstPlayer, mockDice);

        // 2. two categories are filled so total 2 of them are filled.
        assertEquals(NUMBER_OF_CATEGORIES - 2, availableCombinationsMap.size());

        firstPlayer.fillCategory(Type.THREES.getValue(), anyScore);
        firstPlayer.fillCategory(Type.FOURS.getValue(), anyScore);

        availableCombinationsMap =
                mockGame.getScoresOfAvailableCombinations(firstPlayer, mockDice);

        // 3. two more categories are filled so total 4 of them are filled.
        assertEquals(NUMBER_OF_CATEGORIES - 4, availableCombinationsMap.size());
    }

    /*
     * 1. Run 13 rounds
     * 2. Each player chooses whether to roll the dice or choose a
     *      category. (used random function -> 1: category, 2: roll dice)
     * 3. When the player choose to roll the dice, they also face two
     *      options (used random function -> 1: roll all dice, 2: roll certain dice).
     * 4. When each player has selected a category, one round is over and keep going until 13 rounds.
     */
    @Test
    public void fullGameTest() {
        int categoryOptionNumber = 1;
        int rollDiceOptionNumber = 2;
        int categoryOrRollDiceRandomSelection;

        int randomlyChosenCategoryAmongAvailableCategories;

        int rollAllDiceOptionNumber = 1;
        int rollCertainDiceOptionNumber = 2;
        int rollAllOrCertainDiceRandomSelection;

        int numberOfKeptDice;
        int[] randomlyKeptDiceNumbers;

        Random random = new Random();

        while (mockGame.hasGameFinished()) {
//            System.out.println("------------------------------------------------------------------");
//            System.out.println("current turn: " + mockYahtzeeGame.getCurrentRound());

            for (int currentPlayerNumber = 0; currentPlayerNumber < totalNumberOfPlayers; currentPlayerNumber++) {
                // roll all five dice at every start of the turn.
                for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
                    mockDice[i].roll();
                }

                // All values of dice should be among 1, 2, 3, 4, 5, and 6.
                Arrays.stream(mockDice).forEach(e -> assertTrue(AVAILABLE_DICE_VALUES.contains(e.getValue())));

                boolean hasNotPlayerChosenACategory = true;
                while (hasNotPlayerChosenACategory || mockGame.getDiceRetryCount() > NUMBER_OF_AVAILABLE_RETRY_ATTEMPTS) {
                    Map<Integer, Integer> availableCombinationsMap =
                            mockGame.getScoresOfAvailableCombinations(mockPlayers[currentPlayerNumber], mockDice);

                    // Description for the following assertEquals.
                    // When turn increases, the number of available category will decrease because player would have chosen it.
                    assertEquals(mockGame.getCurrentRound(), NUMBER_OF_CATEGORIES - availableCombinationsMap.size());

                    // Choose one -> 1: Category 2: Roll Dice
                    categoryOrRollDiceRandomSelection = (int) (Math.random() * 2 + 1);

                    // If the number of dice attempts exceeds the 3rd, unconditionally choose a category.
                    if (mockGame.getDiceRetryCount() == NUMBER_OF_AVAILABLE_RETRY_ATTEMPTS) {
                        categoryOrRollDiceRandomSelection = categoryOptionNumber;
                    }

                    if (categoryOrRollDiceRandomSelection == categoryOptionNumber) {
                        List<Integer> keysAsArray = new ArrayList<Integer>(availableCombinationsMap.keySet());

                        randomlyChosenCategoryAmongAvailableCategories =
                                keysAsArray.get(random.nextInt(availableCombinationsMap.size()));

                        mockPlayers[currentPlayerNumber].fillCategory(randomlyChosenCategoryAmongAvailableCategories,
                                availableCombinationsMap.get(randomlyChosenCategoryAmongAvailableCategories));

                        // Check that the value, which is located at randomlyChosenCategoryAmongAvailableCategories,
                        // is filled in the array.
                        assertTrue(mockPlayers[currentPlayerNumber].areCategoriesFilled()[randomlyChosenCategoryAmongAvailableCategories]);

                        // Checking available categories (chosen category should not be included).
                        int[] availableCategoriesAfterFilling = mockGame.getAvailableCategoriesOfCurrentPlayer();
                        for (int i = 0; i < availableCategoriesAfterFilling.length; i++) {
                            assertTrue(mockGame.getAvailableCategoriesOfCurrentPlayer()[i] != randomlyChosenCategoryAmongAvailableCategories);
                        }

                        hasNotPlayerChosenACategory = false;
                    } else if (categoryOrRollDiceRandomSelection == rollDiceOptionNumber) {

                        rollAllOrCertainDiceRandomSelection = (int) (Math.random() * 2 + 1);

                        if (rollAllOrCertainDiceRandomSelection == rollAllDiceOptionNumber) {
                            mockGame.rollDice();
                        } else if (rollAllOrCertainDiceRandomSelection == rollCertainDiceOptionNumber) {
                            numberOfKeptDice = (int) (Math.random() * TOTAL_NUMBER_OF_DICE);
                            randomlyKeptDiceNumbers = new int[numberOfKeptDice];

                            // Put any dice that will not be rolled into the array, but should eliminate the duplication.
                            chooseDiceToBeKept(randomlyKeptDiceNumbers);

                            mockGame.rollCertainDice(randomlyKeptDiceNumbers);
                        }
                        mockGame.increaseDiceRetryCount();
                    }
                }
                mockGame.resetDiceRetryCount();
                assertEquals(0, mockGame.getDiceRetryCount());
                mockGame.passTurnToNextPlayer();
            }
            mockGame.increaseRound();
            assertTrue(mockGame.getCurrentRound() <= TOTAL_NUMBER_OF_ROUNDS);
        }

        // Before the calculation, all scores of players should be 0.
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            assertEquals(0, mockPlayers[i].getTotalScore());
        }

        mockGame.calculateScores();

        for (int i = 0; i < totalNumberOfPlayers; i++) {
            assertTrue(mockPlayers[i].getTotalScore() < MAXIMUM_POSSIBLE_SCORE_ATTAINABLE_IN_YAHTZEE_GAME);
        }

        Player[] winners = mockGame.getWinners();

        assertTrue(winners.length >= 1);
    }

    private void chooseDiceToBeKept(int[] randomlyKeptDiceNumbers) {
        for (int i = 0; i < randomlyKeptDiceNumbers.length; i++) {
            randomlyKeptDiceNumbers[i] = (int) (Math.random() * 5);
            for (int j = 0; j < i; j++) {
                if (randomlyKeptDiceNumbers[i] == randomlyKeptDiceNumbers[j]) {
                    i--;
                }
            }
        }
    }
}