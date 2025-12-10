package com.tomwallace.adventofcode2025.problems.solutions.day7;

import com.tomwallace.adventofcode2025.common.Point;
import com.tomwallace.adventofcode2025.utilities.GridUtility;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Beam {
    public Point location;

    public Beam(Integer x, Integer y) {
        this.location = new Point(x, y);
    }

    public MovementResult moveDown(List<List<Character>> grid) {
        var newBeams = new ArrayList<Beam>();
        var newPosition = new Point(this.location.x(), this.location.y() + 1);
        if (!GridUtility.isPointInBounds(newPosition, grid)) {
            return new MovementResult(false, newBeams);
        }
        if (grid.get(newPosition.y()).get(newPosition.x()) == '^') {
            newBeams.add(new Beam(newPosition.x() - 1, newPosition.y()));
            newBeams.add(new Beam(newPosition.x() + 1, newPosition.y()));
            return new MovementResult(true, newBeams);
        }
        newBeams.add(new Beam(newPosition.x(), newPosition.y()));
        return  new MovementResult(false, newBeams);
    }

    @Override
    public String toString() {
        return this.location.x() + "," + this.location.y();
    }
}
