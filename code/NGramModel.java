import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class NGramModel {

    private Map<String, Integer> unigramCounts, bigramCounts;
    private Map<String, Double> bigramProbabilities;
    private MyHashTable bigramTable;
    private MyHashTable unigramTable;
    private MyHashFunction hashFunction;

    public NGramModel() {
        int size = 997; // Size as a prime number
        hashFunction = new PolynomialHashFunction(size, 31); // Example coefficients
        unigramCounts = new HashMap<>();
        bigramCounts = new HashMap<>();
        bigramProbabilities = new HashMap<>();
        unigramTable = new MyHashTable(size, hashFunction);
        bigramTable = new MyHashTable(size, hashFunction);
    }

    public void processText(String filePath) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            String previousWord = null;

            while (scanner.hasNext()) {
                String currentWord = scanner.next().toLowerCase().replaceAll("[^a-zA-Z'.]", "");
                unigramCounts.put(currentWord, unigramCounts.getOrDefault(currentWord, 0) + 1);
                unigramTable.add(currentWord);

                if (previousWord != null && !previousWord.endsWith(".")) {
                    String bigram = previousWord + " " + currentWord;
                    bigramCounts.put(bigram, bigramCounts.getOrDefault(bigram, 0) + 1);
                    bigramTable.add(bigram);
                }

                previousWord = !currentWord.equals(".") ? currentWord : null;
            }
        }
        calculateBigramProbabilities();
    }

    public String predictNextWords(String context, int numWords) {
        if (context == null || context.trim().isEmpty())
            return "No context provided";

        String[] words = context.split("\\s+");
        if (words.length < 1)
            return "Insufficient context";

        StringBuilder prediction = new StringBuilder(context);
        String lastWord = words[words.length - 1].toLowerCase();

        for (int i = 0; i < numWords; i++) {
            String nextWord = getNextMostLikelyWord(lastWord);
            if (nextWord.equals("No prediction")) {
                break;
            }
            prediction.append(" ").append(nextWord);
            lastWord = nextWord;
        }

        return prediction.toString();
    }

    private String getNextMostLikelyWord(String lastWord) {
        String bestNextWord = null;
        double maxProbability = -1.0;

        for (String bigram : bigramProbabilities.keySet()) {
            String[] bigramParts = bigram.split("\\s+");
            if (bigramParts.length > 1 && bigramParts[0].equals(lastWord)) {
                double probability = bigramProbabilities.get(bigram);
                if (probability > maxProbability) {
                    maxProbability = probability;
                    bestNextWord = bigramParts[1];
                }
            }
        }

        return bestNextWord != null ? bestNextWord : "No prediction";
    }

    private void calculateBigramProbabilities() {
        bigramProbabilities.clear();
        for (Map.Entry<String, Integer> entry : bigramCounts.entrySet()) {
            String bigram = entry.getKey();
            int bigramCount = entry.getValue();

            String[] words = bigram.split("\\s+");
            if (words.length < 2)
                continue;

            String firstWord = words[0];
            int firstWordCount = unigramCounts.getOrDefault(firstWord, 0);

            if (firstWordCount > 0) {
                double probability = bigramCount / (double) firstWordCount;
                bigramProbabilities.put(bigram, probability);
            }
        }
    }

    public Map<String, Double> getBigramProbabilities() {
        return new HashMap<>(bigramProbabilities);
    }

    public MyHashTable getUnigramTable() {
        return this.unigramTable;
    }
}
