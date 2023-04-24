package yahtzee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static yahtzee.Die.TOTAL_NUMBER_OF_DICE;

/**
 * This class implements the Yahtzee game, i.e.
 * this is the introductory place to start the game.
 *
 * @author Yoochul Kim
 */
public class YahtzeeGameImpl implements YahtzeeGame {
    // As we need to fill 13 categories
    public static final int TOTAL_NUMBER_OF_ROUNDS = 13;

    /**
     * The maximum possible score attainable in this Yahtzee game is 414 points.
     * (1x6) + (2x6) + (3x6) + (4x6) + (5x6) + (6x6) + UPPER_SECTION_BONUS(35)
     * three of a kind(6x6) + four of a kind(6x6) + full house(25) +
     * small straight(30) + large straight(40) + yahtzee(50) + chance(6x6) = 414
     */
    public static final int MAXIMUM_POSSIBLE_SCORE_ATTAINABLE_IN_YAHTZEE_GAME = 414;

    private Player[] players;

    // the five dices
    private Die[] dice;

    private int totalNumberOfPlayers;

    // There are 13 rounds in the game, so it will be increased by
    // 1 when every player choose a category in each round.
    private int currentRound;

    // There is 2 more attempts for rolling dice.
    private int diceRetryCount;

    // the player who has a turn.
    private int currentPlayerNumber;

    private Player[] winners;

    // If there are 3 players, the last number should 2 as the array starts at zero.
    private int lastPlayerNumber;

    public static int MINIMUM_NUMBER_OF_PLAYERS = 1;


    @Override
    public void makeGame(String[] playerNames) {

        if (playerNames.length <= MINIMUM_NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException("Number of player is not enough to play the game.");
        }

        // Set totalNumberOfPlayers.
        totalNumberOfPlayers = playerNames.length;
        // If five names come, the number of players' object should be five.
        players = new Player[totalNumberOfPlayers];
        for (int i = 0; i < totalNumberOfPlayers; i++) {
            players[i] = new Player(playerNames[i]);
        }

        // Five dice are created.
        dice = new Die[TOTAL_NUMBER_OF_DICE];
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            dice[i] = new Die();
        }

        // It goes up by 1 till 13.
        currentRound = 0;
        diceRetryCount = 0;
        currentPlayerNumber = 0;
        lastPlayerNumber = totalNumberOfPlayers - 1;
    }

    @Override
    public Player[] getPlayers() {
        return players;
    }

    @Override
    public int getCurrentRound() {
        return currentRound;
    }

    @Override
    public int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

    @Override
    public void increaseRound() {
        currentRound++;
    }

    @Override
    public Die[] getDice() {
        return dice;
    }

    public void rollDice() {
        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            dice[i].roll();
        }
    }

    @Override
    public void rollCertainDice(int[] keptDice) {
        for (int ketpDie : keptDice) {
            if (ketpDie < Die.FIRST_DIE_NUMBER_IN_THE_ARRAY || Die.LAST_DIE_NUMBER_IN_THE_ARRAY < ketpDie) {
                throw new IllegalArgumentException("wrong dice selection");
            }
        }

        boolean[] areDiceKept = new boolean[TOTAL_NUMBER_OF_DICE];
        Arrays.fill(areDiceKept, false);

        for (int keptDieNumber : keptDice) {
            areDiceKept[keptDieNumber] = true;
        }

        for (int i = 0; i < TOTAL_NUMBER_OF_DICE; i++) {
            if (!areDiceKept[i]) {
                dice[i].roll();
            }
        }
    }

    @Override
    public void resetDiceRetryCount() {
        diceRetryCount = 0;
    }

    @Override
    public void increaseDiceRetryCount() {
        diceRetryCount++;
    }

    @Override
    public int getDiceRetryCount() {
        return diceRetryCount;
    }

    @Override
    public void calculateScores() {
        for (Player playerTemp : players) {
            playerTemp.calculateTotalScore();
        }
    }

    @Override
    public Player[] getWinners() {
        int[] totalScores = new int[totalNumberOfPlayers];

        for (int i = 0; i < totalNumberOfPlayers; i++) {
//            System.out.println(players[i].getName() + "'s total score: " + players[i].getTotalScore());
            totalScores[i] = players[i].getTotalScore();
        }

        int[] rank = new int[totalNumberOfPlayers];
        Arrays.fill(rank, 1);

        for (int i = 0; i < totalNumberOfPlayers; i++) {
            int standardScore = totalScores[i];

            for (int j = i + 1; j < totalNumberOfPlayers; j++) {
                int comparisonScore = totalScores[j];

                if (standardScore < comparisonScore) {
                    rank[i]++;
                } else if (standardScore > comparisonScore) {
                    rank[j]++;
                }
            }
        }
        List<Integer> winnerList = new ArrayList<>();

        for (int i = 0; i < rank.length; i++) {
            if (rank[i] == 1) {
                winnerList.add(i);
            }
        }

        int numberOfWinners = winnerList.size();
        winners = new Player[numberOfWinners];
        for (int i = 0; i < numberOfWinners; i++) {
            winners[i] = players[winnerList.get(i)];
        }

        return winners;
    }

    @Override
    public Map<Integer, Integer> getScoresOfAvailableCombinations(Player player, Die[] dice) {
        return player.getScoresOfAvailableCombinations(dice);
    }

    @Override
    public int[] getAvailableCategoriesOfCurrentPlayer() {
        return players[currentPlayerNumber].getAvailableCategories();
    }

    @Override
    public int getCurrentPlayerNumber() {
        return currentPlayerNumber;
    }

    @Override
    public void passTurnToNextPlayer() {
        if (currentPlayerNumber >= lastPlayerNumber) {
            currentPlayerNumber = 0;
        } else {
            currentPlayerNumber++;
        }
    }

    @Override
    public boolean hasGameFinished() {
        return getCurrentRound() < TOTAL_NUMBER_OF_ROUNDS;
    }

    @Override
    public void fillCategory(int categoryNumber, int score) {
        players[currentPlayerNumber].fillCategory(categoryNumber, score);
    }
}