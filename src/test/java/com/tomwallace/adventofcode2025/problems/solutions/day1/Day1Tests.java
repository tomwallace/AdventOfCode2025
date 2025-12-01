package com.tomwallace.adventofcode2025.problems.solutions.day1;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Tests {

    @ParameterizedTest
    @CsvSource({"50,L68,82","82,L30,52","52,R48,0","99,R1,0"})
    public void findNewDialPosition(Integer start, String input, Integer expected) {
        var sut = new Day1();
        var result = sut.findNewDialPosition(start, input);

        assertEquals(expected, result.dialPosition());
    }

    @Test
    public void calculateSimilarityScore_True() {
        var filePath = FileUtility.testDataPath + "Day1TestInputA.txt";
        var sut = new Day1();
        var result = sut.countTimesDialAtZero(filePath, true);

        assertEquals(3, result);
    }

    @Test
    public void calculateSimilarityScore_False() {
        var filePath = FileUtility.testDataPath + "Day1TestInputA.txt";
        var sut = new Day1();
        var result = sut.countTimesDialAtZero(filePath, false);

        assertEquals(6, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day1();
        var result = sut.partA();

        assertEquals("1089", result);
    }

    // 6536 too high
    @Test
    public void partB_Actual() {
        var sut = new Day1();
        var result = sut.partB();

        assertEquals("6530", result);
    }
}
