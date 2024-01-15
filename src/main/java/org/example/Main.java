package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> strings = loadFile("src/main/resources/input.txt");

        List<String> alphabetSorted = sortByAlphabet(strings);
        saveFile(alphabetSorted, "src/main/resources/alphabetSorted.txt");

        List<String> charSorted = sortByNumberChar(strings);
        saveFile(charSorted, "src/main/resources/charSorted.txt");

        List<String> sortByWord = sortByWord(strings, 2);
        saveFile(sortByWord, "src/main/resources/sortByWord.txt");
    }

    private static List<String> loadFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveFile(List<String> strings, String path) {

        try (FileWriter writer = new FileWriter(path)) {
            for (String s: strings) {
                writer.write(s);
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> sortByAlphabet(List<String> strings) {
        return strings.stream()
                .sorted(Main::compare)
                .collect(Collectors.toList());
    }

    private static int compare(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int len = Math.min(s1.length(), s2.length());
        for (int i = 0; i < len; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return s1.charAt(i) - s2.charAt(i);
            }
        }
        return 0;
    }

    private static List<String> sortByNumberChar(List<String> strings) {
        return strings.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
    }

    private static List<String> sortByWord(List<String> strings, int num) {
        return strings.stream()
                .sorted((s1, s2) -> compare(s1.split(" ")[num-1], s2.split(" ")[num-1]))
                .collect(Collectors.toList());
    }

}