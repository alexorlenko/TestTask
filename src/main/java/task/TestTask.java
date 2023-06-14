package task;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class TestTask {

    static SortedMap<Long, Integer> bid = new TreeMap<>();

    static SortedMap<Long, Integer> ask = new TreeMap<>();

    static final String input = "input.txt";

    static final String output = "output.txt";

    static StringBuilder getOutput = new StringBuilder();

    public static void main(String[] args) throws IOException {
        TestTask.workWithText(input);
        writer(getOutput);
    }

    public static void workWithText(String fileName) throws IOException {
        List<String> lines = readerOfLines(fileName);
        for (String element : lines) {
            String[] elements = element.split(",");
            switch (elements[0]) {
                case "u":
                    if (elements[3].equals("bid")) {
                        addToBidMap(elements);
                    } else {
                        addToAskMap(elements);
                    }
                    break;
                case "q":
                    if (elements[1].equals("best_bid")) {
                        printBestBid();
                    } else if (elements[1].equals("best_ask")) {
                        printBestAsk();
                    } else {
                        printSizeOfPrice(Long.parseLong(elements[2]));
                    }
                    break;
                case "o":
                    if (elements[1].equals("buy")) {
                        buy(Integer.parseInt(elements[2]));
                    } else {
                        sell(Integer.parseInt(elements[2]));
                    }
                    break;
            }
        }
    }

    public static List<String> readerOfLines(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    static void writer(StringBuilder string) throws IOException {
        FileWriter fileWriter = new FileWriter(output);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(string);
        printWriter.close();
    }

    static void addToBidMap(String[] elements) {
        if (bid.containsKey(Long.parseLong(elements[1]))) {
            int value = bid.get(Long.parseLong(elements[1]));
            int sum = value + Integer.parseInt(elements[2]);
            bid.put(Long.parseLong(elements[1]), sum);
        } else {
            bid.put(Long.parseLong(elements[1]), Integer.parseInt(elements[2]));
        }
    }

    static void addToAskMap(String[] elements) {
        if (ask.containsKey(Long.parseLong(elements[1]))) {
            int value = ask.get(Long.parseLong(elements[1]));
            int sum = value + Integer.parseInt(elements[2]);
            ask.put(Long.parseLong(elements[1]), sum);
        } else {
            ask.put(Long.parseLong(elements[1]), Integer.parseInt(elements[2]));
        }
    }

    static void printBestBid() {
        getOutput.append(bid.lastKey()).append(",").append(bid.get(bid.lastKey())).append("\n");
    }

    static void printBestAsk() {
        getOutput.append(ask.lastKey()).append(",").append(ask.get(ask.lastKey())).append("\n");
    }

    static void printSizeOfPrice(Long price) {
        if (bid.get(price) == null) {
            getOutput.append(ask.get(price)).append("\n");
        } else {
            getOutput.append(bid.get(price)).append("\n");
        }
    }

    static void buy(int number) {
        ask.put(ask.firstKey(), ask.get(ask.firstKey()) - number);
    }

    static void sell(int number) {
        bid.put(bid.lastKey(), bid.get(bid.lastKey()) - number);
    }
}
