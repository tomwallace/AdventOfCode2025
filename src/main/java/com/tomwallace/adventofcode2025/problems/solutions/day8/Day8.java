package com.tomwallace.adventofcode2025.problems.solutions.day8;

import com.tomwallace.adventofcode2025.common.ThreeDPoint;
import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.*;
import java.util.stream.Collectors;

public class Day8 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Playground";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 8; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day8Input.txt";
        var result = multiplyLargestCircuits(filePath, 1000);
        return result.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day8Input.txt";
        var result = getLastConnectionLocMultiplied(filePath);
        return result.toString();
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

    protected Long multiplyLargestCircuits(String filePath, Integer numToCollect) {
        var boxes = getBoxes(filePath);
        var sorted = sortDistances(boxes);
        var circuits = createCircuits(boxes);
        var iterator = sorted.entrySet().iterator();
        for (var i = 0; i < numToCollect; i++) {
            Map.Entry<String, Double> current = iterator.next();
            var pointA = current.getKey().split(";")[0];
            var pointB = current.getKey().split(";")[1];
            var found = false;
            String otherMatch = null;
            for (var circuit : circuits) {
                if (circuit.contains(pointA) && circuit.contains(pointB)) {
                    found = true;
                    break;
                } else if (circuit.contains(pointA)) {
                    found = true;
                    circuit.add(pointB);
                    otherMatch = pointB;
                    break;
                } else if (circuit.contains(pointB)) {
                    found = true;
                    circuit.add(pointA);
                    otherMatch = pointA;
                    break;
                }
            }
            if (!found) {
                var newCircuit = new HashSet<String>();
                newCircuit.add(pointA);
                newCircuit.add(pointB);
                circuits.add(newCircuit);
            } else if (otherMatch != null) {
                reduceCircuits(otherMatch, circuits);
            }
        }
        var sortedCircuits = circuits.stream()
                .map(HashSet::size)
                .sorted(Comparator.reverseOrder())
                .toList();

        return sortedCircuits.stream()
                .limit(3)
                .mapToLong(Integer::longValue)
                .reduce(1L, (a, b) -> a * b);
    }

    protected Long getLastConnectionLocMultiplied(String filePath) {
        var boxes = getBoxes(filePath);
        var sorted = sortDistances(boxes);
        var circuits = createCircuits(boxes);
        var iterator = sorted.entrySet().iterator();
        var lastConnector = "";
        do {
            Map.Entry<String, Double> current = iterator.next();
            lastConnector = current.getKey();
            var pointA = current.getKey().split(";")[0];
            var pointB = current.getKey().split(";")[1];
            var found = false;
            String otherMatch = null;
            for (var circuit : circuits) {
                if (circuit.contains(pointA) && circuit.contains(pointB)) {
                    found = true;
                    break;
                } else if (circuit.contains(pointA)) {
                    found = true;
                    circuit.add(pointB);
                    otherMatch = pointB;
                    break;
                } else if (circuit.contains(pointB)) {
                    found = true;
                    circuit.add(pointA);
                    otherMatch = pointA;
                    break;
                }
            }
            if (!found) {
                var newCircuit = new HashSet<String>();
                newCircuit.add(pointA);
                newCircuit.add(pointB);
                circuits.add(newCircuit);
            } else if (otherMatch != null) {
                reduceCircuits(otherMatch, circuits);
            }
        } while (circuits.size() > 1);
        var split = lastConnector.split(";");
        var xOne = Long.parseLong(split[0].split(",")[0]);
        var xTwo = Long.parseLong(split[1].split(",")[0]);
        return xOne * xTwo;
    }

    private List<ThreeDPoint> getBoxes(String filePath) {
        return FileUtility.parseFileToList(filePath, line -> {
            var split = line.split(",");
            return new ThreeDPoint(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        });
    }

    private void reduceCircuits(String point, ArrayList<HashSet<String>> circuits) {
        var firstIndex = -1;
        for (var i = 0; i < circuits.size(); i++) {
            if (circuits.get(i).contains(point)) {
                if (firstIndex == -1) {
                    firstIndex = i;
                } else {
                    circuits.get(firstIndex).addAll(circuits.get(i));
                    circuits.remove(i);
                    i--;
                }
            }
        }
    }

    private Double findStraightLineDistance(ThreeDPoint pointA, ThreeDPoint pointB) {
        return Math.abs(Math.sqrt(Math.pow(pointA.x() - pointB.x(), 2) + Math.pow(pointA.y() - pointB.y(), 2) + Math.pow(pointA.z() - pointB.z(), 2)));
    }

    private String getPointsIndex(ThreeDPoint pointA, ThreeDPoint pointB) {
        var comparison = pointA.toString().compareTo(pointB.toString());
        if (comparison < 0) {
            return pointA+ ";"  + pointB;
        } else if (comparison > 0) {
            return pointB+ ";"  + pointA;
        } else {
            return pointA + ";"  + pointB;
        }
    }

    private ArrayList<HashSet<String>> createCircuits(List<ThreeDPoint> boxes) {
        var circuits = new ArrayList<HashSet<String>>();
        for (var box : boxes) {
            var hs = new HashSet<String>();
            hs.add(box.toString());
            circuits.add(hs);
        }
        return circuits;
    }

    private LinkedHashMap<String, Double>  sortDistances(List<ThreeDPoint> boxes) {
        var distances = new LinkedHashMap<String, Double>();
        for (var i  = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                if (i == j) {
                    continue;
                }
                var dist = findStraightLineDistance(boxes.get(i), boxes.get(j));
                distances.put(getPointsIndex(boxes.get(i), boxes.get(j)), dist);
            }
        }
        return distances.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
