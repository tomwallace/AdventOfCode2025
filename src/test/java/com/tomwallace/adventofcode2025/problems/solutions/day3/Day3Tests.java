package com.tomwallace.adventofcode2025.problems.solutions.day3;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Tests {

    @ParameterizedTest
    @CsvSource({"987654321111111,98","811111111111119,89","234234234234278,78","818181911112111,92"})
    public void largestJoltageInBank(String input, Long expected) {
        var sut = new Day3();
        var result = sut.largestJoltageInBank(input, 2);

        assertEquals(expected, result);
    }

    @Test
    public void sumJoltageAcrossBanks() {
        var filePath = FileUtility.testDataPath + "Day3TestInputA.txt";
        var sut = new Day3();
        var result = sut.sumJoltageAcrossBanks(filePath, 2);

        assertEquals(357L, result);
    }

    @Test
    public void sumJoltageAcrossBanks_Big() {
        var filePath = FileUtility.testDataPath + "Day3TestInputA.txt";
        var sut = new Day3();
        var result = sut.sumJoltageAcrossBanks(filePath, 12);

        assertEquals(3121910778619L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day3();
        var result = sut.partA();

        assertEquals("17244", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day3();
        var result = sut.partB();

        assertEquals("171435596092638", result);
    }
}
