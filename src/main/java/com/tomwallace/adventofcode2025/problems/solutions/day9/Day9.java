package com.tomwallace.adventofcode2025.problems.solutions.day9;

import com.tomwallace.adventofcode2025.common.Point;
import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.ArrayList;
import java.util.Comparator;
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
        var area = findBiggestAreaInPolygon(filePath);
        return area.toString();
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

    protected Long findLargestArea(String filePath) {
        var redTiles = getRedTiles(filePath);
        var rectangles = getRectangles(redTiles);
        var sortedPairs = rectangles.stream()
                .sorted(Comparator.comparingLong(Rectangle::area).reversed())
                .toList();
        return sortedPairs.getFirst().area();
    }

    protected Long findBiggestAreaInPolygon(String filePath) {
        var redTiles = getRedTiles(filePath);
        var rectangles = getRectangles(redTiles);
        return findBiggestAreaInPolygon(rectangles, getEdgesOfPolygon(redTiles));
    }

    private List<Point> getRedTiles(String filePath) {
        return FileUtility.parseFileToList(filePath, line -> {
            var split = line.split(",");
            return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        });
    }

    private List<Rectangle> getRectangles(List<Point> corners) {
        var rectangles = new ArrayList<Rectangle>();
        for (var i  = 0; i < corners.size(); i++) {
            for (int j = i + 1; j < corners.size(); j++) {
                if (i == j) {
                    continue;
                }
                rectangles.add(new Rectangle(corners.get(i), corners.get(j)));
            }
        }
        return rectangles;
    }

    private Long findBiggestAreaInPolygon(List<Rectangle> rectangles, List<Edge> polygon) {
        var maxArea = 0L;
        for (var  rectangle : rectangles) {
            if (!rectangle.intersects(polygon)) {
                maxArea = Math.max(maxArea, rectangle.area());
            }
        }
        return maxArea;
    }

    private List<Edge> getEdgesOfPolygon(List<Point> redTiles) {
        final List<Edge> result = new ArrayList<>();
        for (int i = 0; i < redTiles.size(); i++) {
            var current = redTiles.get(i);
            var next = redTiles.get(i < redTiles.size() - 1 ? i + 1 : 0);
            result.add(new Edge(current, next));
        }
        // Connect the last and first to complete the polygon
        result.add(new Edge(redTiles.getLast(), redTiles.getFirst()));
        return result;
    }

    private record Edge(Point r1, Point r2) {}

    private record Rectangle(Point r1, Point r2) {
        public Long area() {
            return (long) (Math.abs(r1.x() - r2.x()) + 1) * (Math.abs(r1.y() - r2.y()) + 1);
        }

        // Based upon https://kishimotostudios.com/articles/aabb_collision/
        public boolean intersects(final List<Edge> edges) {
            final long minX = Math.min(r1.x(), r2.x());
            final long maxX = Math.max(r1.x(), r2.x());
            final long minY = Math.min(r1.y(), r2.y());
            final long maxY = Math.max(r1.y(), r2.y());

            for (final Edge e : edges) {
                final long eMinX = Math.min(e.r1().x(), e.r2().x());
                final long eMaxX = Math.max(e.r1().x(), e.r2().x());
                final long eMinY = Math.min(e.r1().y(), e.r2().y());
                final long eMaxY = Math.max(e.r1().y(), e.r2().y());

                if (minX < eMaxX && maxX > eMinX && minY < eMaxY && maxY > eMinY) {
                    return true;
                }
            }
            return false;
        }
    }
}
