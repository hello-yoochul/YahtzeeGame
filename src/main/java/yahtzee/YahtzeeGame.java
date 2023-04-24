package yahtzee;

import java.util.Map;

/**
 * This interface  is filled up with methods functions for the game.
 *
 * @author Yoochul Kim
 */
public interface YahtzeeGame {
    /**
     * Initialize the game with the specified names.
     *
     * @param playerNames all players' names.
     * @throws IllegalArgumentException if the number of players is not enough.
     */
    public void makeGame(String[] playerNames);

    /**
     * Increase the turn (there are 13 turns).
     */
    public void increaseRound();

    /**
     * Get the five dice in the game.
     *
     * @return dice the five dice in the game.
     */
    public Die[] getDice();

    /**
     * Get the all players' objects in the game.
     *
     * @return players the all players in the game.
     */
    public Player[] getPlayers();

    /**
     * Get the current turn (There are 13 turns in each game).
     *
     * @return currentTurn the current turn.
     */
    public int getCurrentRound();

    /**
     * Get the total number of players.
     *
     * @return totalNumberOfPlayers the total number of players.
     */
    public int getTotalNumberOfPlayers();

    /**
     * Send messages to the players' objects to calculate their own categories' scores.
     * This must be called before calling the getWinners() method.
     */
    public void calculateScores();

    /**
     * Get an array of winner players.
     * If there are two winners, the two objects will be returned.
     * In the end of game, the calculateScores() method must be called before this method.
     *
     * @return winners an array of winner players.
     */
    public Player[] getWinners();

    /**
     * Roll five dice.
     */
    public void rollDice();

    /**
     * Players specifies dice which they do not want to roll.
     *
     * @param keptDice an array of dice which player does not want to roll.
     * @throws IllegalArgumentException when a player choose wrong dice numbers.
     */
    public void rollCertainDice(int[] keptDice);

    /**
     * Reset the dice retry count.
     */
    public void resetDiceRetryCount();

    /**
     * Increase the dice retry count (Each player has three retry attempts of rolling dice).
     */
    public void increaseDiceRetryCount();

    /**
     * Get the dice retry count.
     *
     * @return the dice retry count
     */
    public int getDiceRetryCount();

    /**
     * Get the available combinations of the current dice.
     *
     * @param player who want to calculate the combinations of their dice.
     * @param dice the rolled dice.
     * @return the available combinations of the current dice.
     */
    public Map<Integer,Integer> getScoresOfAvailableCombinations(Player player, Die[] dice);

    /**
     * Get the array numbers of available categories of the current player.
     *
     * @return the array numbers of available categories of the current player.
     */
    public int[] getAvailableCategoriesOfCurrentPlayer();

    /**
     * Get the current player number.
     *
     * @return  the current player number.
     */
    public int getCurrentPlayerNumber();

    /**
     * Pass a turn to the next player.
     */
    public void passTurnToNextPlayer();

    /**
     * Check if the game has finished.
     *
     * @return true if game has finished.
     */
    public boolean hasGameFinished();

    /**
     * Fill the score in the current players score sheet.
     *
     * @param categoryNumber to be filled.
     * @param score to be put.
     */
    public void fillCategory(int categoryNumber, int score);
}
