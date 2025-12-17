package com.tomwallace.adventofcode2025.problems.solutions.day11;

import com.tomwallace.adventofcode2025.problems.Difficulty;
import com.tomwallace.adventofcode2025.problems.IAdventProblemSet;
import com.tomwallace.adventofcode2025.utilities.FileUtility;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day11 implements IAdventProblemSet {

    /***
     * {@inheritDoc}
     */
    public String description() {
        return "Reactor";
    }

    /***
     * {@inheritDoc}
     */
    public Integer sortOrder() { return 11; }

    /***
     * {@inheritDoc}
     */
    public String partA() {
        var filePath = FileUtility.dataPath + "Day11Input.txt";
        var count = countPotentialPaths(filePath);
        return count.toString();
    }

    /***
     * {@inheritDoc}
     */
    public String partB() {
        var filePath = FileUtility.dataPath + "Day11Input.txt";
        var count = countPathsThroughConverterAndTransform(filePath);
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
        return false;
    }

    protected Long countPotentialPaths(String filePath) {
        var connections = getConnections(filePath);
        return pathRecurse("you", "out", connections);
    }

    protected Long countPathsThroughConverterAndTransform(String filePath) {
        var connections = getConnections(filePath);
        var cache = new HashMap<String, Long>();
        return pathRecurseWithNeededNodesAndCache("svr", "out", cache, List.of("dac", "fft"), connections);
    }

    private Long pathRecurse(String nodeKey, String targetKey, Map<String, List<String>> connections) {
        var nextNodes = connections.get(nodeKey);
        if (nextNodes == null || nextNodes.size() == 0) {
            return 0L;
        }
        if (nextNodes.contains(targetKey)) {
            return 1L;
        }
        var result = new AtomicLong();
        connections.get(nodeKey).forEach(n -> result.addAndGet(pathRecurse(n, targetKey, connections)));
        return result.get();
    }

    private Long pathRecurseWithNeededNodesAndCache(String nodeKey, String targetKey, HashMap<String, Long> cache, List<String> neededNodes, Map<String, List<String>> connections) {
        var sb = new StringBuilder(nodeKey);
        var localNeededNodes = new ArrayList<>(neededNodes);
        for (String r : localNeededNodes) {
            sb.append(r);
        }
        var cacheKey = sb.toString();
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }
        if (nodeKey.equals(targetKey)) {
            return localNeededNodes.isEmpty() ? 1L : 0L;
        }
        // localNeededNodes is part of the cache key and it being empty is the end condition
        // so if nodeKey is one of the required ones, remove it for the recursion
        var foundOne = localNeededNodes.contains(nodeKey);
        if (foundOne) {
            localNeededNodes.remove(nodeKey);
        }
        var result = new AtomicLong();
        connections.get(nodeKey).forEach(target -> result.addAndGet(pathRecurseWithNeededNodesAndCache(target, targetKey, cache, localNeededNodes, connections)));
        cache.put(cacheKey, result.get());
        return result.get();
    }

    private Map<String, List<String>> getConnections(String filePath) {
        return FileUtility.parseFileToList(filePath, line -> line)
                .stream()
                .collect(Collectors.toMap(l -> {
                    var splitColon = l.split(":");
                    return splitColon[0].trim();
                }, l -> {
                    var splitColon = l.split(":");
                    return Arrays.stream(splitColon[1].trim().split(" ")).toList();
                }));
    }
}
