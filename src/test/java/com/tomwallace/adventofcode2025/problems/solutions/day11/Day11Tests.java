package com.tomwallace.adventofcode2025.problems.solutions.day11;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11Tests {

    @Test
    public void countPotentialPaths() {
        var filePath = FileUtility.testDataPath + "Day11TestInputA.txt";
        var sut = new Day11();
        var result = sut.countPotentialPaths(filePath);

        assertEquals(5L, result);
    }

    @Test
    public void countPathsThroughConverterAndTransform() {
        var filePath = FileUtility.testDataPath + "Day11TestInputB.txt";
        var sut = new Day11();
        var result = sut.countPathsThroughConverterAndTransform(filePath);

        assertEquals(2L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day11();
        var result = sut.partA();

        assertEquals("571", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day11();
        var result = sut.partB();

        assertEquals("511378159390560", result);
    }
}
