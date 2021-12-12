package no.alexander.adventofcode.day5;

public class Point {
    public final int x;
    public final int y;

    public Point(String input) {
        String[] parts = input.split(",");
        x = Integer.parseInt(parts[0]);
        y = Integer.parseInt(parts[1]);
    }
}
