package com.tomwallace.adventofcode2025.problems.solutions.day5;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Tests {

    @Test
    public void countFreshIngredients() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var result = sut.countFreshIngredients(filePath, false);

        assertEquals(3, result);
    }

    @Test
    public void countFreshIngredients_AllIds() {
        var filePath = FileUtility.testDataPath + "Day5TestInputA.txt";
        var sut = new Day5();
        var result = sut.countFreshIngredients(filePath, true);

        assertEquals(14, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day5();
        var result = sut.partA();

        assertEquals("558", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day5();
        var result = sut.partB();

        assertEquals("344813017450467", result);
    }
}
