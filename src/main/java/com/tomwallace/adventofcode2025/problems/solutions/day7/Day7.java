package com.tomwallace.adventofcode2025.problems.solutions.day7;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.*;

public class Day7 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Laboratories";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 7; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day7Input.txt";
        var count = countTachyonSplits(filePath, false);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day7Input.txt";
        var count = countTachyonSplits(filePath, true);
        return count.toString();
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

    protected Long countTachyonSplits(String filePath, Boolean useAllTimelines) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var beams = new long[grid.getFirst().size()];
        var split = 0L;
        beams[grid.getFirst().indexOf('S')] = 1;
        for (var x = 1; x < grid.size(); x++) {
            var row = grid.get(x);
            for (var column = 0; column < row.size(); column++) {
                // Do we have a splitter?
                if (row.get(column) == '^') {
                    // Do we have a beam to split?
                    if (beams[column] > 0) {
                        // Split the beam
                        split++;
                        beams[column - 1] += beams[column];
                        beams[column + 1] += beams[column];
                        beams[column] = 0;
                    }
                }
            }
        }

        return useAllTimelines ? Arrays.stream(beams).sum() : split;
    }
}

