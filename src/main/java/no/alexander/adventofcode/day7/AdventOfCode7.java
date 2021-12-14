package no.alexander.adventofcode.day7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class AdventOfCode7 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input7.txt")
    private Resource input;

    private List<Integer> positions;

    @PostConstruct
    public void run() {
        LOG.info("********* DAY7 *********");
        parseInput();
        partOne();
        partTwo();
    }

    private void parseInput() {
        try {
            positions = new ArrayList<>();
            for (String line : Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8)) {
                String[] crabs = line.split(",");
                for (String crab : crabs) {
                    positions.add(Integer.valueOf(crab));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        var maxPosition = maxPosition(positions);
        var fuelCost = Integer.MAX_VALUE;

        for (int i = 0; i <= maxPosition; i++) {
            var cost = 0;
            for (Integer p : positions) {
                cost += Math.abs(p - i);
            }
            fuelCost = Math.min(fuelCost, cost);
        }

        LOG.info("Part one - " + fuelCost);
    }

    private int maxPosition(List<Integer> positions) {
        var maxPosition = 0;
        for (Integer p : positions) {
            maxPosition = Math.max(maxPosition, p);
        }
        return maxPosition;
    }

    private void partTwo() {
        var maxPosition = maxPosition(positions);
        var fuelCost = Integer.MAX_VALUE;

        for (int i = 0; i <= maxPosition; i++) {
            var cost = 0;
            for (Integer p : positions) {
                cost += ((Math.abs(p - i) * (Math.abs(p - i) + 1)) / 2);
            }

            fuelCost = Math.min(fuelCost, cost);
        }

        LOG.info("Part two - " + fuelCost);
    }
}
