package com.tomwallace.adventofcode2025.problems.solutions.day8;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Tests {

    @Test
    public void multiplyLargestCircuits() {
        var filePath = FileUtility.testDataPath + "Day8TestInputA.txt";
        var sut = new Day8();
        var result = sut.multiplyLargestCircuits(filePath, 10);

        assertEquals(40L, result);
    }

    @Test
    public void getLastConnectionLocMultiplied() {
        var filePath = FileUtility.testDataPath + "Day8TestInputA.txt";
        var sut = new Day8();
        var result = sut.getLastConnectionLocMultiplied(filePath);

        assertEquals(25272L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day8();
        var result = sut.partA();

        assertEquals("84968", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day8();
        var result = sut.partB();

        assertEquals("8663467782", result);
    }
}
