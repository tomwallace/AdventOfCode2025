package com.tomwallace.adventofcode2025.problems.solutions.day3;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.stream.IntStream;

public class Day3 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Lobby";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 3; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day3Input.txt";
        var sum = sumJoltageAcrossBanks(filePath, 2);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day3Input.txt";
        var sum = sumJoltageAcrossBanks(filePath, 12);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public Difficulty difficulty() {
        return Difficulty.MEDIUM;
    }

    /***
     * {@inheritDoc}
     */
    public Boolean isFavorite() {
        return true;
    }

    protected Long sumJoltageAcrossBanks(String filePath, int joltageSize) {
        var banks = FileUtility.parseFileToList(filePath, line -> line);
        var sum = banks.stream()
                .map(b -> largestJoltageInBank(b, joltageSize))
                .reduce(0L, Long::sum);
        return sum;
    }

    protected Long largestJoltageInBank(String bankString, int joltageSize) {
        var bank = IntStream.range(0, bankString.length())
                .mapToObj(i -> Integer.parseInt(bankString.substring(i, i + 1)))
                .toList();

        var joltageStringBuilder = new StringBuilder();
        var startIndex = 0;
        for (var j = joltageSize; j > 0; j--) {
            var highest = 0;
            var highestIndex = 0;
            // Current digit for joltage can be more than max of j - end of bankString
            for (var i = startIndex; i <= bank.size() - j; i++) {
                var current = bank.get(i);
                if (current > highest) {
                    highest = current;
                    highestIndex = i;
                }
            }
            joltageStringBuilder.append(highest);
            startIndex = highestIndex + 1;
        }

        return Long.parseLong(joltageStringBuilder.toString());
    }
}
