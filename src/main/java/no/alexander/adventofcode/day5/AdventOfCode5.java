package no.alexander.adventofcode.day5;

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
public class AdventOfCode5 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input5.txt")
    private Resource input;

    private List<Line> lines = new ArrayList<>();

    @PostConstruct
    public void run() {
        LOG.info("********* DAY5 *********");
        parseInput();
        partOne();
        partTwo();
    }

    private void parseInput() {
        try {
            for (String line : Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8)) {
                lines.add(new Line(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        Map map = new Map();
        for (Line line : lines) {
            map.addLine(line, true);
        }
//        map.render();

        LOG.info("Part one - " + map.countOverlap());
    }

    private void partTwo() {
        Map map = new Map();
        for (Line line : lines) {
            map.addLine(line, false);
        }
//        map.render();

        LOG.info("Part two - " + map.countOverlap());
    }
}
