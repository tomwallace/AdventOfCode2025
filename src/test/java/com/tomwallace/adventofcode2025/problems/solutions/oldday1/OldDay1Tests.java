package com.tomwallace.adventofcode2025.problems.solutions.oldday1;

import com.tomwallace.adventofcode2025.utilities.FileUtility;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OldDay1Tests {

    @Test
    public void sumListDifference() {
        var filePath = FileUtility.testDataPath + "OldDay1TestInputA.txt";
        var sut = new OldDay1();
        var result = sut.sumListDifference(filePath);

        assertEquals(11, result);
    }

    @Test
    public void calculateSimilarityScore() {
        var filePath = FileUtility.testDataPath + "OldDay1TestInputA.txt";
        var sut = new OldDay1();
        var result = sut.calculateSimilarityScore(filePath);

        assertEquals(31, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new OldDay1();
        var result = sut.partA();

        assertEquals("2375403", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new OldDay1();
        var result = sut.partB();

        assertEquals("23082277", result);
    }
}
