package com.tomwallace.adventofcode2025.problems.solutions.day6;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.ArrayList;
import java.util.List;

public class Day6 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Trash Compactor";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 6; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day6Input.txt";
        var sum = calculateGrandTotal(filePath);
        return sum.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day6Input.txt";
        var sum = calculateGrandTotalUpAndDown(filePath);
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
        return false;
    }

    protected Long calculateGrandTotal(String filePath) {
        var problems = new ArrayList<Problem>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        for (var i = 0; i < lines.size(); i++) {
            var splits = lines.get(i).trim().split("\\s+");
            for (var s = 0; s < splits.length; s++) {
                var split = splits[s];
                if (i == 0) {
                    problems.add(new Problem());
                }
                var current = problems.get(s);
                if (i == (lines.size() - 1)) {
                    current.operator = split;
                } else {
                    current.components.add(Long.parseLong(split));
                }
            }
        }
        return sumGrandTotal(problems);
    }

    protected Long calculateGrandTotalUpAndDown(String filePath) {
        var problems = new ArrayList<Problem>();
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var prob = new Problem();
        for (var x = lines.getFirst().length() - 1; x >= 0; x--) {
            var sb = new StringBuilder();
            for (String line : lines) {
                var current = line.toCharArray()[x];
                if (current == '+' || current == '*') {
                    prob.setOperator(String.valueOf(current));
                } else {
                    sb.append(current);
                }
            }
            var potentialNumber = sb.toString().trim();
            if (potentialNumber.isEmpty()) {
                continue;
            }
            prob.getComponents().add(Long.parseLong(sb.toString().trim()));
            if (prob.getOperator() != null) {
                problems.add(prob);
                prob = new Problem();
            }
        }
        return sumGrandTotal(problems);
    }

    private Long sumGrandTotal(List<Problem> problems) {
        var grandTotal = 0L;
        for (var problem : problems) {
            if (problem.operator.equals("+")) {
                grandTotal += problem.components.stream().mapToLong(Long::longValue).sum();
            } else {
                grandTotal += problem.components.stream().reduce(1L, (a, b) -> a * b);
            }
        }
        return grandTotal;
    }
}