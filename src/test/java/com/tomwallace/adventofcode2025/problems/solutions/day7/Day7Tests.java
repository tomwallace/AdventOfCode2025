package com.tomwallace.adventofcode2025.problems.solutions.day7;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Tests {

    @Test
    public void countTachyonSplits_CountJustSplits() {
        var filePath = FileUtility.testDataPath + "Day7TestInputA.txt";
        var sut = new Day7();
        var result = sut.countTachyonSplits(filePath, false);

        assertEquals(21, result);
    }

    @Test
    public void countTachyonSplits_CountTimelines() {
        var filePath = FileUtility.testDataPath + "Day7TestInputA.txt";
        var sut = new Day7();
        var result = sut.countTachyonSplits(filePath, true);

        assertEquals(40, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day7();
        var result = sut.partA();

        assertEquals("1662", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day7();
        var result = sut.partB();

        assertEquals("40941112789504", result);
    }
}
