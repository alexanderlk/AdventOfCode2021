package no.alexander.adventofcode.day1;

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
public class AdventOfCode1 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input1.txt")
    private Resource input;

    List<Integer> depths = new ArrayList<>();

    @PostConstruct
    public void run() {
        LOG.info("********* DAY1 *********");
        parseInput();
        partOne();
        partTwo();
    }

    private void parseInput() {
        try {
            List<String> lines = Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8);
            for (String line : lines ) {
                depths.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        var previous = depths.get(0);
        var increased = 0;
        for (int i = 1; i < depths.size(); i++) {
            var current = depths.get(i);
            if (current > previous) {
                increased++;
            }

            previous = current;
        }

        LOG.info("Part one - " + increased);
    }

    private void partTwo() {
        var sum1 = depths.get(0) + depths.get(1);
        var sum2 = depths.get(1);
        var sum3 = 0;
        var previous = -1;
        var increased = 0;

        for (int i = 2; i < depths.size(); i++) {
            var value = depths.get(i);
            sum1 += value;
            sum2 += value;
            sum3 += value;



            if ( sum1 != 0 && sum2 != 0 && sum3 != 0) {
                if (previous != -1 && sum1 > previous) {
                    increased++;
                }
                previous = sum1;
                sum1 = sum2;
                sum2 = sum3;
                sum3 = 0;
            }
        }
        LOG.info("Part two - " + increased);
    }
}
