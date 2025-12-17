package com.tomwallace.adventofcode2025.problems.solutions.day12;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;
import lombok.SneakyThrows;

import java.util.*;

public class Day12 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Christmas Tree Farm";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 12; }

    /***
     * {@inheritDoc}
     */
    @SneakyThrows
    public String partA() {
        var filePath = FileUtility.dataPath + "Day12Input.txt";
        var count = countValidRegions(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        return "Need to get all other 23 stars!";
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

    // I started by trying to do all the rotations and permutations to count valid areas, but could never get it to work.
    // I checked Reddit for suggestions and found many people doing a simple area calculation for each shape and that worked.
    // So, I implemented that.
    protected Integer countValidRegions(String filePath) throws Exception {
        var lines = FileUtility.parseFileToList(filePath, line -> line);
        var shapes = new ArrayList<Shape>();
        var index = -1;
        var hashes = 0;
        var indexOfAreas = -1;
        for (var i = 0; i < lines.size(); i++) {
            var line = lines.get(i);
            if (line.contains("x")) {
                indexOfAreas = i;
                break;
            }
            if (line.isEmpty()) {
                if (index == -1 || hashes == 0) {
                    throw new Exception("Shape will not be created correctly");
                }
                shapes.add(new Shape(index, hashes));
                index = -1;
                hashes = 0;
                continue;
            }
            if (line.contains(":")) {
                index = Integer.parseInt(line.substring(0, line.indexOf(":")));
                continue;
            }
            hashes += (int) line.chars().filter(c -> c == '#').count();
        }

        var validRegions = 0;
        for (var i = indexOfAreas; i < lines.size(); i++) {
            if (isValidRegion(lines.get(i), shapes)) {
                validRegions++;
            }
        }
        return validRegions;
    }

    private Boolean isValidRegion(String line, List<Shape> shapes) {
        var splitColon = line.split(":");
        var targetArea = Arrays.stream(splitColon[0].split("x"))
                .map(Integer::parseInt)
                .reduce(1, (a, b) -> a * b);
        var neededShapes =  Arrays.stream(splitColon[1].trim().split(" "))
                .map(Integer::parseInt)
                .toList();
        var regionArea = 0;
        for (var i = 0; i < neededShapes.size(); i++) {
            var localArea = shapes.get(i).area;
            regionArea += localArea * neededShapes.get(i);
        }
        return regionArea <= targetArea;
    }

    private record Shape(Integer index, Integer area) {}
}
