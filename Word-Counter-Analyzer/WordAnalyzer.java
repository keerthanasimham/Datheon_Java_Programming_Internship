import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class WordAnalyzer {

    public static void main(String[] args) {

        String filename = "sample_article.txt";

        StringBuilder text = new StringBuilder();

        try (BufferedReader reader =
                new BufferedReader(new FileReader(filename))) {

            String line;

            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }

        } catch (IOException e) {

            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        String content = text.toString().toLowerCase();

        String[] words = content.split("\\W+");

        HashMap<String, Integer> frequency = new HashMap<>();

        int totalWords = 0;

        for (String word : words) {

            if (!word.isEmpty()) {

                totalWords++;

                frequency.put(
                    word,
                    frequency.getOrDefault(word, 0) + 1
                );
            }
        }

        int uniqueWords = frequency.size();

        String[] sentences = content.split("[.!?]+");

        int sentenceCount = 0;

        for (String sentence : sentences) {
            if (!sentence.trim().isEmpty()) {
                sentenceCount++;
            }
        }

        double averageSentenceLength = 0;

        if (sentenceCount > 0) {
            averageSentenceLength =
                (double) totalWords / sentenceCount;
        }

        System.out.println("\n========================================");
        System.out.println("       WORD COUNTER AND ANALYZER");
        System.out.println("========================================");

        System.out.println("Total Words: " + totalWords);
        System.out.println("Unique Words: " + uniqueWords);
        System.out.println("Total Sentences: " + sentenceCount);

        System.out.printf(
            "Average Sentence Length: %.2f words%n",
            averageSentenceLength
        );

        List<Map.Entry<String, Integer>> entries =
            new ArrayList<>(frequency.entrySet());

        entries.sort(
            Comparator.comparingInt(
                (Map.Entry<String, Integer> entry) -> entry.getValue()
            ).reversed().thenComparing(Map.Entry::getKey)
        );

        System.out.println("\n========== TOP 10 WORDS ==========");

        System.out.printf("%-20s %-10s%n", "WORD", "COUNT");

        int limit = Math.min(10, entries.size());

        for (int i = 0; i < limit; i++) {

            Map.Entry<String, Integer> entry = entries.get(i);

            System.out.printf(
                "%-20s %-10d%n",
                entry.getKey(),
                entry.getValue()
            );
        }

        System.out.println("========================================");
        System.out.println("Text analysis completed successfully!");
    }
}