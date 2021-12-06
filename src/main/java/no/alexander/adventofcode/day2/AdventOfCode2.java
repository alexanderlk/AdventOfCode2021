package no.alexander.adventofcode.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdventOfCode2 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input2.txt")
    private Resource input;

    private List<Command> commands = new ArrayList<>();

    @PostConstruct
    public void run() {
        LOG.info("********* DAY2 *********");
        parseInput();
        partOne();
        partTwo();
    }

    private void parseInput() {
        try {
            List<String> lines = Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8);
            for (String line : lines ) {
                commands.add(new Command(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        var p = new Position();
        for (Command c : commands){
            c.apply(p);
        }

        LOG.info("Part one - " + p.multiply());
    }

    private void partTwo() {
        var p = new Position();
        for (Command c : commands){
            c.applyTwo(p);
        }

        LOG.info("Part two - " + p.multiply());
    }

    private class Position {
        long horizontal = 0;
        long depth = 0;
        long aim = 0;

        BigInteger multiply() {
            return BigInteger.valueOf(horizontal).multiply(BigInteger.valueOf(depth));
        }
    }

    private class Command {
        private final Direction direction;
        private final int distance;

        private Command(String command) {
            String[] parts = command.split(" ");
            direction = Direction.parse(parts[0]);
            distance = Integer.valueOf(parts[1]);
        }

        void apply(Position p) {
            switch (direction) {
                case UP:
                    p.depth -= distance;
                    break;
                case DOWN:
                    p.depth += distance;
                    break;
                case FORWARD:
                    p.horizontal += distance;
                    break;
            }
        }

        void applyTwo(Position p) {
            switch (direction) {
                case UP:
                    p.aim -= distance;
                    break;
                case DOWN:
                    p.aim += distance;
                    break;
                case FORWARD:
                    p.horizontal += distance;
                    p.depth += (p.aim * distance);
                    break;
            }
        }
    }

    private enum Direction {
        FORWARD("forward"),
        DOWN("down"),
        UP("up");

        private String direction;

        Direction(String direction) {
            this.direction = direction;
        }

        static Direction parse(String direction) {
            for (Direction d : values()) {
                if (d.direction.equals(direction)) {
                    return d;
                }
            }
            return null;
        }
    }


}
