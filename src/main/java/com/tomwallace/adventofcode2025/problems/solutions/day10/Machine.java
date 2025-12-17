package com.tomwallace.adventofcode2025.problems.solutions.day10;

import java.util.*;
import java.util.stream.IntStream;

public class Machine {
    private final String targetLights;
    private final List<List<Integer>> buttons;
    private final List<Integer> joltageRequirements;
    private final Boolean[][] buttonInfluences;
    private Map<List<Integer>, Integer> joltageIncrToNrPresses;

    // ex: [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
    public Machine(String input) {
        var splitBracket = input.split("]");
        targetLights = splitBracket[0].substring(1);
        var splitCurly = splitBracket[1].split("[{}]");
        joltageRequirements = Arrays.stream(splitCurly[1].split(","))
                .map(Integer::parseInt)
                .toList();
        buttons = Arrays.stream(splitCurly[0].trim().split("[()]"))
                .filter(g -> !g.isEmpty() && !g.equals(" "))
                .map(g -> Arrays.stream(g.split(","))
                        .map(Integer::parseInt)
                        .toList())
                .toList();

        buttonInfluences = new Boolean[buttons.size()][joltageRequirements.size()];
        for (int i = 0; i < buttons.size(); i++) {
            final Boolean[] influence = new Boolean[joltageRequirements.size()];
            Arrays.fill(influence, false);
            final List<Integer> button = buttons.get(i);
            for (final int j : button) {
                influence[j] = true;
            }
            buttonInfluences[i] = influence;
        }
        calculatePatterns();
    }

    public Integer countMinimumButtonClicksLights() {
        var queue = new PriorityQueue<State>(Comparator.comparingInt(a -> a.clicks));
        queue.add(new State(0, targetLights.replace('#', '.')));
        while (!queue.isEmpty()) {
            var state = queue.poll();
            for (var i = 0; i < buttons.size(); i++) {
                var newLight = clickButton(i, state.current);
                if (newLight.equals(targetLights)) {
                    return state.clicks + 1;
                }
                queue.add(new State(state.clicks + 1, newLight));
            }
        }
        // should never get here
        return -1;
    }

    private String clickButton(Integer buttonIndex, String current) {
        var chars = current.toCharArray();
        for (var index : buttons.get(buttonIndex)) {
            chars[index] = toggleChar(chars[index]);
        }
        return String.valueOf(chars);
    }

    private char toggleChar(char c) {
        if (c == '#') {
            return '.';
        }
        return '#';
    }

    // I tried several different implementations of the Part B problem, and made no progress.  In looking through Reddit, I found this potential solution:
    // https://www.reddit.com/r/adventofcode/comments/1pk87hl/2025_day_10_part_2_bifurcate_your_way_to_victory/
    // as implemented (and used here slightly modified):
    // https://github.com/ash42/adventofcode/blob/main/adventofcode2025/src/nl/michielgraat/adventofcode2025/day10/Manual.java
    public int getNrOfPressesForVoltages() {
        return getNrOfPressesForVoltages(joltageRequirements, new HashMap<>());
    }

    private int getNrOfPressesForVoltages(final List<Integer> current, final Map<List<Integer>, Integer> memoMap) {
        if (current.stream().allMatch(i -> i == 0)) {
            return 0;
        }
        if (memoMap.containsKey(current)) {
            return memoMap.get(current);
        }

        int result = Integer.MAX_VALUE;
        for (final Map.Entry<List<Integer>, Integer> entry : joltageIncrToNrPresses.entrySet()) {
            final List<Integer> pattern = entry.getKey();
            if (isValid(current, pattern)) {
                // Initialize the new goal as a list of 0's.
                final List<Integer> newGoal = new ArrayList<>(current.size());
                IntStream.range(0, current.size()).forEach(i -> newGoal.add(0));

                for (int i = 0; i < pattern.size(); i++) {
                    newGoal.set(i, (current.get(i) - pattern.get(i)) / 2);
                }

                final int nr = getNrOfPressesForVoltages(newGoal, memoMap);
                if (nr < Integer.MAX_VALUE) {
                    result = Math.min(result, entry.getValue() + (2 * nr));
                }
            }
        }
        memoMap.put(current, result);
        return result;
    }

    private boolean isValid(final List<Integer> current, final List<Integer> pattern) {
        boolean valid = true;
        for (int i = 0; i < pattern.size(); i++) {
            final int incr = pattern.get(i);
            final int cur = current.get(i);
            if (!(incr <= cur && incr % 2 == cur % 2)) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    private void calculatePatterns() {
        // We want to calculate all patterns arising from every button combination
        // pressed.
        // How do we do this?
        // If there are 3 buttons than we can represent them by their index:
        // 0 1 2 3
        // Than we can just any combination of them (0 = not pressed, 1 = press)
        // 0 0 0 1
        // 0 0 1 0
        // 0 0 1 1 etc.
        // This is just binary. So for 3 buttons there are 2^3 = 8 combinations.
        final int nrPatterns = (int) Math.pow(2, buttons.size());

        // Calculate:
        // - The light pattern a certain combination of button presses can lead to (for
        // part 1).
        // - The joltage increases a certain combination of button presses can lead to
        // (for part 2).
        Map<List<Boolean>, Integer> lightPatternToNrPresses = new HashMap<>();
        joltageIncrToNrPresses = new HashMap<>();

        // Loop over all combinations of button presses
        for (int i = 0; i < nrPatterns; i++) {
            // Initialize the resulting lists as lists of false's and 0's respectively.
            final List<Boolean> lightPattern = new ArrayList<>();
            final List<Integer> joltageIncreases = new ArrayList<>();
            IntStream.range(0, joltageRequirements.size()).forEach(j -> lightPattern.add(false));
            IntStream.range(0, joltageRequirements.size()).forEach(j -> joltageIncreases.add(0));

            final String binary = getBinaryRepresentation(i);
            int nrButtonsPressed = 0;
            // Press all the buttons
            for (int buttonNr = 0; buttonNr < binary.length(); buttonNr++) {
                if (binary.charAt(buttonNr) == '1') {
                    // Get the meters which are influenced by this button.
                    final Boolean[] influence = buttonInfluences[buttonNr];

                    for (int k = 0; k < influence.length; k++) {
                        lightPattern.set(k, (!influence[k] || !lightPattern.get(k)) && (influence[k] || lightPattern.get(k)));

                        joltageIncreases.set(k, influence[k] ? joltageIncreases.get(k) + 1 : joltageIncreases.get(k));
                    }
                    nrButtonsPressed++;
                }
            }
            // Add the pattern/joltage increases if the map does not contain the pattern yet
            // or replace it when it has a button press count which is greater than the one
            // we just calculated.
            if (!lightPatternToNrPresses.containsKey(lightPattern)
                    || lightPatternToNrPresses.get(lightPattern) > nrButtonsPressed) {
                lightPatternToNrPresses.put(lightPattern, nrButtonsPressed);
            }
            if (!joltageIncrToNrPresses.containsKey(joltageIncreases)
                    || joltageIncrToNrPresses.get(joltageIncreases) > nrButtonsPressed) {
                joltageIncrToNrPresses.put(joltageIncreases, nrButtonsPressed);
            }
        }
    }

    private String getBinaryRepresentation(final int i) {
        final StringBuilder sb = new StringBuilder(Integer.toBinaryString(i)).reverse();
        while (sb.length() < buttons.size()) {
            sb.append('0');
        }
        return sb.substring(0, buttons.size());
    }

    private record State(Integer clicks, String current) {}
}
