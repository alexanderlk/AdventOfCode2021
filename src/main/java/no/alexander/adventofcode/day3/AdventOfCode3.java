package no.alexander.adventofcode.day3;

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
public class AdventOfCode3 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input3.txt")
    private Resource input;

    private List<String> bits;

    @PostConstruct
    public void run() {
        LOG.info("********* DAY3 *********");
        parseInput();
        partOne();
        partTwo();
    }

    private void parseInput() {
        try {
            bits = Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        var length = bits.get(0).length();

        var gammaRate = "";
        var epsilonRate = "";

        for (int i = 0; i < length; i++ ) {
            var count0 = 0;
            var count1 = 0;

            for (String bit : bits) {
                if (bit.charAt(i) == '0') {
                    count0++;
                } else {
                    count1++;
                }
            }
            if (count1 > count0) {
                gammaRate += "1";
                epsilonRate += "0";
            } else {
                gammaRate += "0";
                epsilonRate += "1";
            }
        }

        var gamma = Integer.parseInt(gammaRate, 2);
        var epsilon = Integer.parseInt(epsilonRate, 2);
        LOG.info("Part one - " + (gamma * epsilon));

    }

    private void partTwo() {
        var length = bits.get(0).length();

        String oxygenRating = findOxygenRating(length, 0, bits);
        String co2ScrubberRating = findCO2Rating(length, 0, bits);

        LOG.info("Part two - " + (Integer.parseInt(oxygenRating, 2) * Integer.parseInt(co2ScrubberRating, 2)));
    }

    private String findOxygenRating(int length, int index, List<String> bits) {
        List<String> tmp = new ArrayList<>();

        var count0 = 0;
        var count1 = 0;

        for (String b : bits) {
            if (b.charAt(index) == '0') {
                count0++;
            } else {
                count1++;
            }
        }

        var target = count0 > count1 ? '0' : '1';
        for (String b : bits) {
            if (b.charAt(index) == target) {
                tmp.add(b);
            }
        }

        if (tmp.size() == 1) {
            return tmp.get(0);
        }

        return findOxygenRating(length, ++index, tmp);
    }

    private String findCO2Rating(int length, int index, List<String> bits) {
        List<String> tmp = new ArrayList<>();

        var count0 = 0;
        var count1 = 0;

        for (String b : bits) {
            if (b.charAt(index) == '0') {
                count0++;
            } else {
                count1++;
            }
        }

        var target = count1 >= count0 ? '0' : '1';
        for (String b : bits) {
            if (b.charAt(index) == target) {
                tmp.add(b);
            }
        }

        if (tmp.size() == 1) {
            return tmp.get(0);
        }

        return findCO2Rating(length, ++index, tmp);
    }
}
