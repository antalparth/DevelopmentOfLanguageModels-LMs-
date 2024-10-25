public class SimpleHashFunction extends MyHashFunction {
    public SimpleHashFunction(int tableSize) {
        super(tableSize);
    }

    @Override
    public int hash(String word) {
        // We assume the word is not empty and hash based on the first character
        return (int) word.charAt(0) % tableSize;
    }

}
