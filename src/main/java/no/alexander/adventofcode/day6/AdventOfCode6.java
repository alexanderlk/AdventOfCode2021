package no.alexander.adventofcode.day6;

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
public class AdventOfCode6 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input6.txt")
    private Resource input;

    private List<BigInteger> fish;

    @PostConstruct
    public void run() {
        LOG.info("********* DAY6 *********");
        partOne();
        partTwo();
    }

    private void parseInput() {
        fish = new ArrayList<>();
        for (int i = 0; i <= 8; i++) {
            fish.add(BigInteger.ZERO);
        }
        try {
            for (String line : Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8)) {
                String[] fishes = line.split(",");
                for (String s : fishes) {
                    Integer timer = Integer.valueOf(s);
                    fish.set(timer, fish.get(timer).add(BigInteger.ONE));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        parseInput();
        simulate(80);
        LOG.info("Part one - " + countFish());
    }

    private void simulate(int days) {
        for (int i = 0; i < days; i++) {
            var spawned = fish.get(0);
            fish.remove(0);
            fish.add(spawned);
            fish.set(6, fish.get(6).add(spawned));
        }
    }

    private BigInteger countFish() {
        var sum = BigInteger.ZERO;
        for (BigInteger count : fish) {
            sum = sum.add(count);
        }
        return sum;
    }

    private void partTwo() {
        parseInput();
        simulate(256);
        LOG.info("Part two - " + countFish());
    }
}
