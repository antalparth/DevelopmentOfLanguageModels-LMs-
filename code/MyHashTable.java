
import java.util.*;

public class MyHashTable {
    private MyLinkedObject[] buckets;
    private MyHashFunction hashFunction;

    public MyHashTable(int size, MyHashFunction hashFunction) {
        this.buckets = new MyLinkedObject[size];
        this.hashFunction = hashFunction;
    }

    public void add(String word) {
        int index = hashFunction.hash(word);
        if (buckets[index] == null) {
            buckets[index] = new MyLinkedObject(word);
        } else {
            buckets[index].setWord(word);
        }
    }

    public MyLinkedObject getBucket(int index) {
        return buckets[index];
    }

    // Method to get the statistics of word occurrences
    public Map<String, Integer> getWordStatistics() {
        Map<String, Integer> statistics = new HashMap<>();
        for (MyLinkedObject bucket : buckets) {
            MyLinkedObject current = bucket;
            while (current != null) {
                statistics.put(current.getWord(), current.getCount());
                current = current.getNext();
            }
        }
        return statistics;
    }

    // Method for debugging: prints the entire hash table
    public void printHashTable() {
        for (int i = 0; i < buckets.length; i++) {
            System.out.print("Bucket " + i + ": ");
            MyLinkedObject current = buckets[i];
            while (current != null) {
                System.out.print("[" + current.getWord() + ", " + current.getCount() + "] -> ");
                current = current.getNext();
            }
            System.out.println("null");
        }
    }

    public int getCount(String wordSequence) {
        int index = hashFunction.hash(wordSequence);
        MyLinkedObject current = buckets[index];
        while (current != null) {
            if (current.getWord().equals(wordSequence)) {
                return current.getCount();
            }
            current = current.getNext();
        }
        return 0; // Word sequence not found
    }

    // Method to get all words in the hash table
    public List<MyLinkedObject> getAllWords() {
        List<MyLinkedObject> wordsList = new ArrayList<>();
        for (MyLinkedObject head : buckets) {
            MyLinkedObject current = head;
            while (current != null) {
                wordsList.add(current);
                current = current.getNext();
            }
        }
        return wordsList;
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
    }

    public int getTableSize() {
        return buckets.length;
    }

}
