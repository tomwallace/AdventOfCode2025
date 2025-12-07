package com.tomwallace.adventofcode2025.problems.solutions.day6;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Tests {

    @Test
    public void calculateGrandTotal() {
        var filePath = FileUtility.testDataPath + "Day6TestInputA.txt";
        var sut = new Day6();
        var result = sut.calculateGrandTotal(filePath);

        assertEquals(4277556L, result);
    }

    @Test
    public void calculateGrandTotalUpAndDown() {
        var filePath = FileUtility.testDataPath + "Day6TestInputA.txt";
        var sut = new Day6();
        var result = sut.calculateGrandTotalUpAndDown(filePath);

        assertEquals(3263827L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day6();
        var result = sut.partA();

        assertEquals("6891729672676", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day6();
        var result = sut.partB();

        assertEquals("9770311947567", result);
    }
}
