package no.alexander.adventofcode.day5;

import java.util.ArrayList;
import java.util.List;

public class Map {
    public List<List<Integer>> map = new ArrayList<>();

    public void addLine(Line line, boolean straightOnly) {
        resizeGrid(line);

        var dx = line.getDeltaX();
        var dy = line.getDeltaY();

        if (straightOnly && dx != 0 && dy != 0) {
            return;
        }

        var distance = dy != 0 ? Math.abs(line.start.y - line.end.y) : Math.abs(line.start.x - line.end.x);

        for (int i = 0; i <= distance; i++) {
            var y = line.start.y + (dy * i);
            var x = line.start.x + (dx * i);
            List<Integer> l = map.get(y);
            l.set(x, l.get(x) + 1);
        }
    }

    private void resizeGrid(Line line) {
        var endX = Math.max(line.start.x, line.end.x);
        var endY = Math.max(line.start.y, line.end.y);

        for (int i = map.size(); i <= endY; i++) {
            map.add(new ArrayList<>());
        }

        for (List<Integer> l : map) {
            for (int i = l.size(); i <= endX; i++) {
                l.add(0);
            }
        }
    }

    public void render() {
        for (List<Integer> line : map) {
            for (Integer point : line) {
                System.out.print(point);
            }
            System.out.println();
        }
    }

    public int countOverlap() {
        var overlap = 0;
        for (List<Integer> line : map) {
            for (Integer point : line) {
                if (point > 1) {
                    overlap++;
                }
            }
        }

        return overlap;
    }
}
