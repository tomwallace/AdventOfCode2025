package com.tomwallace.adventofcode2025.problems.solutions.day2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class Day2Tests {

    private final String TEST_INPUT = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124";

    @ParameterizedTest
    @CsvSource({"11","22","99","1010","1188511885","222222"})
    public void isValidId_False(Long input) {
        var sut = new Day2();
        var result = sut.isValidId(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({"12","123456","1324579"})
    public void isValidId_True(Long input) {
        var sut = new Day2();
        var result = sut.isValidId(input);

        assertTrue(result);
    }

    @ParameterizedTest
    @CsvSource({"11","22","99","111","999","565656","2121212121"})
    public void isValidIdAnyRepeat_False(Long input) {
        var sut = new Day2();
        var result = sut.isValidIdAnyRepeat(input);

        assertFalse(result);
    }

    @ParameterizedTest
    @CsvSource({"12","123456","1324579"})
    public void isValidIdAnyRepeat_True(Long input) {
        var sut = new Day2();
        var result = sut.isValidIdAnyRepeat(input);

        assertTrue(result);
    }

    @Test
    public void findInvalidIds_One() {
        var input = "11-22";
        var sut = new Day2();
        var result = sut.findInvalidIds(input, false);

        assertEquals(2, result.size());
        assertTrue(result.contains(11L));
        assertTrue(result.contains(22L));
    }

    @Test
    public void findInvalidIds_Two() {
        var input = "998-1012";
        var sut = new Day2();
        var result = sut.findInvalidIds(input, false);

        assertEquals(1, result.size());
        assertTrue(result.contains(1010L));
    }

    @Test
    public void findInvalidIds_Three() {
        var input = "1188511880-1188511890";
        var sut = new Day2();
        var result = sut.findInvalidIds(input, false);

        assertEquals(1, result.size());
        assertTrue(result.contains(1188511885L));
    }

    @ParameterizedTest
    @CsvSource({"95-115,2,111","998-1012,2,999","1188511880-1188511890,1,1188511885","222220-222224,1,222222"})
    public void findInvalidIds_UseAnyRepeat(String input, int numExpected, Long expectedId) {
        var sut = new Day2();
        var result = sut.findInvalidIds(input, true);

        assertEquals(numExpected, result.size());
        assertTrue(result.contains(expectedId));
    }

    @Test
    public void sumInvalidIdsAcrossRanges() {
        var sut = new Day2();
        var result = sut.sumInvalidIdsAcrossRanges(TEST_INPUT, false);

        assertEquals(1227775554L, result);
    }

    @Test
    public void sumInvalidIdsAcrossRanges_AnyRepeat() {
        var sut = new Day2();
        var result = sut.sumInvalidIdsAcrossRanges(TEST_INPUT, true);

        assertEquals(4174379265L, result);
    }

    @Test
    public void partA_Actual() {
        var sut = new Day2();
        var result = sut.partA();

        assertEquals("23039913998", result);
    }

    @Test
    public void partB_Actual() {
        var sut = new Day2();
        var result = sut.partB();

        assertEquals("35950619148", result);
    }
}
