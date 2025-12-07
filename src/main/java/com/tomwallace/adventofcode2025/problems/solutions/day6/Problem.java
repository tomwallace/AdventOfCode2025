package com.tomwallace.adventofcode2025.problems.solutions.day6;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Problem {
    public List<Long> components;
    public String operator;

    public Problem() {
        components = new ArrayList<>();
    }
}
