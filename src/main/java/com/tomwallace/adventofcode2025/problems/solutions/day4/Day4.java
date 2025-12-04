package com.tomwallace.adventofcode2025.problems.solutions.day4;

import com.tomwallace.adventofcode2025.common.Point;
import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;
import com.tomwallace.adventofcode2025.utilities.GridUtility;

import java.util.ArrayList;
import java.util.List;

public class Day4 implements IAdventProblemSet {

    public final Character ROLL = '@';
    public final Character EMPTY = '.';

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Printing Department";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 4; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day4Input.txt";
        var count = countAccessibleRolls(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day4Input.txt";
        var count = removeAndCountAccessibleRolls(filePath);
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

    protected Integer countAccessibleRolls(String filePath) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var accessibleRolls = findAccessibleRolls(grid);
        return accessibleRolls.size();
    }

    protected Integer removeAndCountAccessibleRolls(String filePath) {
        var grid = FileUtility.parseFileToListListCharacter(filePath);
        var removedRolls = 0;
        var someRemoved = false;
        do {
            var accessibleRolls = findAccessibleRolls(grid);
            // Exit condition
            if (accessibleRolls.isEmpty()) {
                return removedRolls;
            }

            removedRolls += accessibleRolls.size();
            // Alter the grid to remove the rolls
            for (var accessibleRoll : accessibleRolls) {
                grid.get(accessibleRoll.y()).set(accessibleRoll.x(), EMPTY);
            }

        } while (true);
    }

    private List<Point> findAccessibleRolls(List<List<Character>> grid) {
        var accessibleRolls = new ArrayList<Point>();
        for (var y = 0; y < grid.size(); y++) {
            for (var x = 0; x < grid.getFirst().size(); x++) {
                if (grid.get(y).get(x) != ROLL) {
                    continue;
                }

                var current = new Point(x, y);
                var neighbourRolls = GridUtility.findAllNeighbours(current, grid).stream()
                        .filter(n -> grid.get(n.y()).get(n.x()).equals(ROLL))
                        .count();
                if (neighbourRolls < 4) {
                    accessibleRolls.add(current);
                }
            }
        }
        return accessibleRolls;
    }
}
