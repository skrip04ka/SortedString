package org.example;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<String> strings = loadFile("src/main/resources/input.txt");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите номер соотвествующий варианту сортировки:");
        System.out.println("\t1 - Сортировка по алфавиту");
        System.out.println("\t2 - Сортировка по колличеству символов");
        System.out.println("\t3 - Сортировка по заданному слову");
        System.out.println("\tДля выхода - любая клавиша");
        String choice = scanner.next();

        List<String> result;

        switch (choice) {
            case "1" -> result = sortByAlphabet(strings);
            case "2" -> result = sortByNumberChar(strings);
            case "3" -> {
                System.out.println("Введите номер слова");
                result = sortAndCountByWord(strings, scanner.nextInt());
            }
            default -> {
                return;
            }
        }

        saveFile(result, "src/main/resources/result.txt");
        System.out.println("Результат записан в файл result.txt");
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
            for (String s : strings) {
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

    private static List<String> sortByNumberChar(List<String> strings) {
        return strings.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
    }

    private static List<String> sortAndCountByWord(List<String> strings, int num) {
        List<String> result = strings.stream()
                .sorted((s1, s2) -> compareWord(s1, s2, num))
                .toList();
        return countWord(result, num);
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

    private static List<String> countWord(List<String> strings, int num) {
        Map<String, Integer> map = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (String s : strings) {
            String[] sArr = s.split(" ");
            if (sArr.length >= num) {
                map.put(sArr[num - 1], map.getOrDefault(sArr[num - 1], 0) + 1);
            }
        }

        for (int i = 0; i < strings.size(); i++) {
            String[] sArr = strings.get(i).split(" ");
            if (sArr.length >= num) {
                result.add(strings.get(i) + " " + map.get(sArr[num - 1]));
            } else result.add(i, strings.get(i) + " " + "-1");
        }

        return result;
    }

    private static int compareWord(String s1, String s2, int num) {
        String[] s1arr = s1.split(" ");
        String[] s2arr = s2.split(" ");

        if (s1arr.length < num && s2arr.length >= num) return 1;
        else if (s1arr.length >= num && s2arr.length < num) return -1;
        else if (s1arr.length < num) return 0;
        else return compare(s1arr[num - 1], s2arr[num - 1]);
    }

}