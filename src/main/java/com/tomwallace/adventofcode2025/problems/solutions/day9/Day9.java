package com.tomwallace.adventofcode2025.problems.solutions.day9;

import com.tomwallace.adventofcode2025.common.Point;
import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.List;

public class Day9 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Movie Theater";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 9; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day9Input.txt";
        var area = findLargestArea(filePath);
        return area.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day9Input.txt";
        //var count = countFreshIngredients(filePath, true);
        return "";
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

    protected Long findLargestArea(String filePath) {
        var points = FileUtility.parseFileToList(filePath, line -> {
            var split = line.split(",");
            return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        });
        var largestArea = 0L;
        for (var i  = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if (i == j) {
                    continue;
                }
                var area = findRectangleArea(points.get(i), points.get(j));
                if (area > largestArea) {
                    largestArea = area;
                }
            }
        }

        return largestArea;
    }

    protected Long findLargestRectangleAreaInPerimeter(String filePath) {
        // TODO: TW - return after thought
        return 0L;
    }

    private long findRectangleArea(Point a, Point b) {
        var sideOne = Math.abs(a.x() - b.x()) + 1;
        var sideTwo = Math.abs(a.y() - b.y()) + 1;
        return (long) sideOne * sideTwo;
    }

    private boolean isPointInAnyPair(List<Pair> pairs, Point point) {
        return pairs.stream().anyMatch(p -> isPointInRectangle(p, point));
    }

    private boolean isPointInRectangle(Pair pair, Point target) {
        var minX = Math.min(pair.a().x(), pair.b().x());
        var minY = Math.min(pair.a().y(), pair.b().y());
        var maxX = Math.max(pair.a().x(), pair.b().x());
        var maxY = Math.max(pair.a().y(), pair.b().y());
        return target.x() >= minX && target.x() <= maxX && target.y() >= minY && target.y() <= maxY;
    }

    private record Pair(Point a, Point b) {}
}
