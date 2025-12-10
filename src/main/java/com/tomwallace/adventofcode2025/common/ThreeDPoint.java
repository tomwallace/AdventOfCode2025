package com.tomwallace.adventofcode2025.common;

public record ThreeDPoint(Integer x, Integer y, Integer z) {
    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }
}
