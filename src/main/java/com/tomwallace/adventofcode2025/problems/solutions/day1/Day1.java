package com.tomwallace.adventofcode2025.problems.solutions.day1;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

public class Day1 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Secret Entrance";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() {
        return 1;
    }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day1Input.txt";
        var count = countTimesDialAtZero(filePath, true);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day1Input.txt";
        var count = countTimesDialAtZero(filePath, false);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.EASY;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Integer countTimesDialAtZero(String filePath, boolean onlyEndPositions) {
        var instructions = FileUtility.parseFileToList(filePath, line -> line);
        var currentPosition = 50;
        var timesAtZero = 0;
        var allTimesAtZero = 0;
        for (var inst : instructions) {
            var result = findNewDialPosition(currentPosition, inst);

            currentPosition = result.dialPosition;
            allTimesAtZero += result.timesPastZero;
            if (currentPosition == 0) {
                timesAtZero++;
                allTimesAtZero++;
            }
        }
        return onlyEndPositions ? timesAtZero : allTimesAtZero;
    }

    protected Result findNewDialPosition(Integer currentPosition, String input) {
        var direction = input.charAt(0);
        var modifier = Integer.parseInt(input.substring(1));
        var mod = modifier % 100;
        var div = (int) modifier / 100;
        if (direction == 'R') {
            var newPosition = currentPosition + mod;
            if (newPosition > 99) {
                var updatedPosition = newPosition - 100;
                return new Result(updatedPosition, updatedPosition == 0 ? div : div + 1);
            }
            return new Result(newPosition, div);
        } else {
            var newPosition = currentPosition - mod;
            if (newPosition < 0) {
                return new Result(100 + newPosition,  currentPosition == 0 ? div : div + 1);
            }
            return new Result(newPosition, div);
        }
    }

    protected record Result(Integer dialPosition, Integer timesPastZero) {
    }
}
