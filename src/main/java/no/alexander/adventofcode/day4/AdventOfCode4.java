package no.alexander.adventofcode.day4;

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
public class AdventOfCode4 {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Value("classpath:input4.txt")
    private Resource input;

    private List<BingoBoard> boards;
    private List<Integer> draw;

    @PostConstruct
    public void run() {
        LOG.info("********* DAY4 *********");
        parseInput();
        partOne();
        partTwo();
    }

    private void parseInput() {
        try {
            draw = new ArrayList<>();
            boards = new ArrayList<>();
            List<String> content = Files.readAllLines(input.getFile().toPath(), StandardCharsets.UTF_8);
            for (String s : content.get(0).split(",")) {
                draw.add(Integer.valueOf(s));
            }

            BingoBoard board = null;
            for (int i = 1; i < content.size(); i++) {
                String line = content.get(i);
                if (line.isEmpty()) {
                    board = new BingoBoard();
                    boards.add(board);
                } else {
                    board.addLine(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void partOne() {
        parseInput();
        int score = findAndCalculateScore();
        LOG.info("Part one - " + score);
    }

    private int findAndCalculateScore() {
        for (int value : draw) {
            markBoards(value);

            for (BingoBoard board : boards) {
                if (board.hasMarkedLine()) {
                    return board.unmarkedSum() * value;
                }
            }
        }

        return -1;
    }

    private void markBoards(int value) {
        for (BingoBoard board : boards) {
            board.mark(value);
        }
    }

    private void partTwo() {
        parseInput();
        int score = 0;
        for (int value : draw) {
            markBoards(value);

            List<BingoBoard> tmp = new ArrayList<>();
            for (BingoBoard board : boards) {
                if (!board.hasMarkedLine()) {
                    tmp.add(board);
                }
            }

            if (boards.size() == 1 && tmp.isEmpty()) {
                score = boards.get(0).unmarkedSum() * value;
                break;
            }
            boards = tmp;
        }
        LOG.info("Part two - " + score);
    }

    private class BingoBoard {
        private List<List<Square>> board = new ArrayList<>();

        public BingoBoard() {
        }

        public BingoBoard(BingoBoard original) {
            for (List<Square> squares : original.board) {
                List<Square> copySquares = new ArrayList<>();
                board.add(copySquares);
                for (Square square : squares) {
                    copySquares.add(new Square(square.number));
                }
            }
        }

        private void addLine(String line) {
            List<Square> values = new ArrayList<>();
            board.add(values);
            for (String val : line.trim().split("\\s+")) {
                values.add(new Square(Integer.valueOf(val)));
            }
        }

        private int unmarkedSum() {
            int sum = 0;
            for (List<Square> squares : board) {
                for (Square square : squares) {
                    if (!square.marked) {
                        sum += square.number;
                    }
                }
            }
            return sum;
        }

        private void mark(int value) {
            for (List<Square> squares : board) {
                for (Square square : squares) {
                    square.mark(value);
                }
            }
        }

        private boolean hasMarkedLine() {
            for (List<Square> line : board) {
                boolean fullLine = true;
                for (Square value : line) {
                    if (fullLine && !value.marked) {
                        fullLine = false;
                    }
                }
                if (fullLine) {
                    return true;
                }
            }

            int size = board.get(0).size();
            for (int i = 0; i < size; i++) {
                boolean fullLine = true;
                for (List<Square> squares : board) {
                    Square value = squares.get(i);
                    if (fullLine && !value.marked) {
                        fullLine = false;
                    }
                }
                if (fullLine) {
                    return true;
                }
            }

            return false;
        }
    }

    private class Square {
        private final int number;
        private boolean marked;

        private Square(int value) {
            number = value;
            marked = false;
        }

        private void mark(int value) {
            if (number == value) {
                marked = true;
            }
        }
    }

}
