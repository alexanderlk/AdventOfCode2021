package no.alexander.adventofcode.day5;

public class Line {
    public final Point start;
    public final Point end;

    public Line(String input) {
        String[] parts = input.split(" -> ");
        start = new Point(parts[0]);
        end = new Point(parts[1]);
    }

    public boolean isHorizontal() {
        return start.y == end.y;
    }

    public boolean isVertical() {
        return start.x == end.x;
    }

    public int getDeltaX() {
        return Integer.compare(end.x, start.x);
    }

    public int getDeltaY() {
        return Integer.compare(end.y, start.y);
    }
}
