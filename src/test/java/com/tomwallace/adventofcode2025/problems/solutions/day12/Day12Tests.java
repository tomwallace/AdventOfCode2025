package com.tomwallace.adventofcode2025.problems.solutions.day12;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Tests {

    @Test
    public void findLargestArea() throws Exception {
        var filePath = FileUtility.testDataPath + "Day12TestInputA.txt";
        var sut = new Day12();
        var result = sut.countValidRegions(filePath);

        assertEquals(3, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day12();
        var result = sut.partA();

        assertEquals("517", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day12();
        var result = sut.partB();

        assertEquals("Need to get all other 23 stars!", result);
    }
}
