package com.tomwallace.adventofcode2025.problems.solutions.day4;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Tests {

    @Test
    public void countAccessibleRolls() {
        var filePath = FileUtility.testDataPath + "Day4TestInputA.txt";
        var sut = new Day4();
        var result = sut.countAccessibleRolls(filePath);

        assertEquals(13, result);
    }

    @Test
    public void removeAndCountAccessibleRolls() {
        var filePath = FileUtility.testDataPath + "Day4TestInputA.txt";
        var sut = new Day4();
        var result = sut.removeAndCountAccessibleRolls(filePath);

        assertEquals(43, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day4();
        var result = sut.partA();

        assertEquals("1491", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day4();
        var result = sut.partB();

        assertEquals("8722", result);
    }
}
