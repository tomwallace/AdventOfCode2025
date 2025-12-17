package com.tomwallace.adventofcode2025.problems.solutions.day9;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Tests {

    @Test
    public void findLargestArea() {
        var filePath = FileUtility.testDataPath + "Day9TestInputA.txt";
        var sut = new Day9();
        var result = sut.findLargestArea(filePath);

        assertEquals(50L, result);
    }

    @Test
    public void findBiggestAreaInPolygon() {
        var filePath = FileUtility.testDataPath + "Day9TestInputA.txt";
        var sut = new Day9();
        var result = sut.findBiggestAreaInPolygon(filePath);

        assertEquals(24L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day9();
        var result = sut.partA();

        assertEquals("4755429952", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day9();
        var result = sut.partB();

        assertEquals("1429596008", result);
    }
}
