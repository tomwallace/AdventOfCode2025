package com.tomwallace.adventofcode2025.problems.solutions.day2;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class Day2 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Gift Shop";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 2; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day2Input.txt";
        var input = FileUtility.parseFileToList(filePath, line -> line).getFirst();
        var sum = sumInvalidIdsAcrossRanges(input, false);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day2Input.txt";
        var input = FileUtility.parseFileToList(filePath, line -> line).getFirst();
        var sum = sumInvalidIdsAcrossRanges(input, true);
        return sum.toString();
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

    protected Long sumInvalidIdsAcrossRanges(String input, Boolean useAnyRepeat) {
        var split = input.split(",");
        var total = 0L;
        for (String s : split) {
            var rangeTotal = findInvalidIds(s, useAnyRepeat).stream().mapToLong(Long::valueOf).sum();
            total += rangeTotal;
        }

        return total;
    }

    protected List<Long> findInvalidIds(String idRange, Boolean useAnyRepeat) {
        var invalidIds = new ArrayList<Long>();
        var split = idRange.split("-");
        var start = Long.parseLong(split[0]);
        var end = Long.parseLong(split[1]);
        for (var i = start; i <= end; i++) {
            var isValid = useAnyRepeat ? isValidIdAnyRepeat(i) : isValidId(i);
            if (!isValid) {
                invalidIds.add(i);
            }
        }

        return invalidIds;
    }

    protected Boolean isValidId(Long id) {
        var idString = id.toString();
        var idLength = idString.length();
        // Odd lengths will always be valid
        if (idLength % 2 != 0) {
            return true;
        }
        var firstHalf = idString.substring(0, idLength / 2);
        var secondHalf = idString.substring(idLength / 2);
        return !firstHalf.equals(secondHalf);
    }

    // In this case, to be invalid, some pattern of consecutive characters must be repeated exactly across the whole string
    protected Boolean isValidIdAnyRepeat(Long id) {
        var idString = id.toString();
        var idLength = idString.length();
        for (var i = 0; i < idLength - 1; i++) {
            var pattern = idString.substring(0, i + 1);
            if (stringComposedEntirelyOfPattern(pattern, idString)) {
                return false;
            }
        }

        return true;
    }

    private Boolean stringComposedEntirelyOfPattern(String pattern, String entire) {
        for (var i = 0; i < entire.length(); i += pattern.length()) {
            if (i + pattern.length() > entire.length()) {
                return false;
            }
            var target = entire.substring(i, i + pattern.length());
            if (!target.equals(pattern)) {
                return false;
            }
        }

        return true;
    }
}
