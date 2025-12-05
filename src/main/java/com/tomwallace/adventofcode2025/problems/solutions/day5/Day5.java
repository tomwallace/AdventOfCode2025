package com.tomwallace.adventofcode2025.problems.solutions.day5;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class Day5 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Cafeteria";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 5; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day5Input.txt";
        var count = countFreshIngredients(filePath, false);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day5Input.txt";
        var count = countFreshIngredients(filePath, true);
        return count.toString();
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

    protected Long countFreshIngredients(String filePath, Boolean returnAllPossibleIds) {
        var ranges = getRanges(filePath);
        var freshIngredientIds = getFreshIngredientIds(filePath);
        var collapsed = collapseRangeRecurse(ranges);

        // Using only the fresh IDs provided - Part A
        var countFreshIngredients = 0L;
        for (var id  : freshIngredientIds) {
            if (isFreshIngredient(id, collapsed)) {
                countFreshIngredients++;
            }
        }
        // Figure out all possible fresh IDs - Part B
        var countAllPossibleIds = 0L;
        for (var range : collapsed) {
            countAllPossibleIds += range.end - range.start + 1;
        }
        return returnAllPossibleIds ? countAllPossibleIds : countFreshIngredients;
    }

    private List<Range> getRanges(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var ranges = new ArrayList<Range>();
        for (var line : lines) {
            if (line == null || line.isEmpty()) {
                return ranges;
            }
            var split = line.split("-");
            var begin = Long.parseLong(split[0]);
            var end = Long.parseLong(split[1]);
            ranges.add(new Range(begin, end));
        }
        // Should never get here
        return ranges;
    }

    private List<Long> getFreshIngredientIds(String filePath) {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var ingredientIds = new ArrayList<Long>();
        var changeToIngredients = false;
        for (var line : lines) {
            if (line == null || line.isEmpty()) {
                changeToIngredients = true;
                continue;
            }
            if (changeToIngredients) {
                ingredientIds.add(Long.parseLong(line));
            }
        }
        return ingredientIds;
    }

    // Determines if the ingredient ID is contained within any of the fresh ingredient ranges
    private Boolean isFreshIngredient(Long id, List<Range> ranges) {
        for (var  range : ranges) {
            if (id >= range.start && id <= range.end) {
                return true;
            }
        }
        return false;
    }

    // Recurse over the ranges until they are collapsed without further change
    private List<Range> collapseRangeRecurse(List<Range> ranges) {
        var anyModificationDone = false;
        var collapsed = ranges;
        do {
            var output = collapseRanges(collapsed);
            collapsed = output.ranges;
            anyModificationDone = output.modificationOccurred;
        } while (anyModificationDone);

        return collapsed;
    }

    // Collapses the ranges by looking at all situations of overlap:
    // next range hangs over start, next range hangs over end, next range is completely subsumed,
    // next range completely subsumes, and no overlap at all
    private CollapseOutput collapseRanges(List<Range> originalRanges) {
        var collapsed = new ArrayList<Range>();
        var anyModificationDone = false;
        for (var target : originalRanges) {
            boolean updated = false;
            for (var i = 0; i < collapsed.size(); i++) {
                var current = collapsed.get(i);
                // If overlap is below current range and end is within it
                if (target.start <= current.start && target.end >= current.start && target.end <= current.end) {
                    anyModificationDone = true;
                    updated = true;
                    collapsed.set(i, new Range(target.start, current.end));
                }
                // If overlap is above current range and start is within it
                else if (target.start >= current.start && target.start <= current.end && target.end >= current.end) {
                    anyModificationDone = true;
                    updated = true;
                    collapsed.set(i, new Range(current.start, target.end));
                }
                // If overlap completely consumes current range
                else if (target.start <= current.start && target.end >= current.end) {
                    anyModificationDone = true;
                    updated = true;
                    collapsed.set(i, new Range(target.start, target.end));
                }
                // If the current version completely contains range, then say updated to not add it
                else if (target.start >= current.start && target.end <= current.end) {
                    anyModificationDone = true;
                    updated = true;
                }
            }
            if (!updated) {
                collapsed.add(target);
            }
        }

        return new CollapseOutput(collapsed, anyModificationDone);
    }

    private record Range(Long start, Long end) {}
    private record CollapseOutput(ArrayList<Range> ranges, Boolean modificationOccurred) {}
}
