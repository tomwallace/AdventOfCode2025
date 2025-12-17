package com.tomwallace.adventofcode2025.problems.solutions.day10;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.*;

public class Day10 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Factory";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 10; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day10Input.txt";
        var sum = sumMinimumButtonClicks(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day10Input.txt";
        var sum = sumMinimumButtonClicksJoltage(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.HARD;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return false;
    }

    protected Integer sumMinimumButtonClicks(String filePath) {
        var machines = FileUtility.parseFileToList(filePath, Machine::new);
        return machines.stream()
                .map(Machine::countMinimumButtonClicksLights)
                .reduce(0, Integer::sum);
    }

    protected Integer sumMinimumButtonClicksJoltage(String filePath) {
        var machines = FileUtility.parseFileToList(filePath, Machine::new);
        return machines.stream()
                .map(Machine::getNrOfPressesForVoltages)
                .reduce(0, Integer::sum);
    }
}

