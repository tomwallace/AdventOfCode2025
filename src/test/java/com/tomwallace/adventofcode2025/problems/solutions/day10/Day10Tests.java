package com.tomwallace.adventofcode2025.problems.solutions.day10;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10Tests {

    @Test
    public void machine_CountMinimumButtonClicksLights_One() {
        var input = "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}";
        var sut = new Machine(input);
        var result = sut.countMinimumButtonClicksLights();

        assertEquals(2, result);
    }

    @Test
    public void machine_CountMinimumButtonClicksLights_Two() {
        var input = "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}";
        var sut = new Machine(input);
        var result = sut.countMinimumButtonClicksLights();

        assertEquals(3, result);
    }

    @Test
    public void sumMinimumButtonClicks() {
        var filePath = FileUtility.testDataPath + "Day10TestInputA.txt";
        var sut = new Day10();
        var result = sut.sumMinimumButtonClicks(filePath);

        assertEquals(7, result);
    }

    @Test
    public void sumMinimumButtonClicksJoltage() {
        var filePath = FileUtility.testDataPath + "Day10TestInputA.txt";
        var sut = new Day10();
        var result = sut.sumMinimumButtonClicksJoltage(filePath);

        assertEquals(33, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day10();
        var result = sut.partA();

        assertEquals("419", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day10();
        var result = sut.partB();

        assertEquals("18369", result);
    }
}
