public class FirstLetterHashFunction extends MyHashFunction {
    public FirstLetterHashFunction(int tableSize) {
        super(tableSize);
    }

    @Override
    public int hash(String word) {
        int hash = 0;
        for (char c : word.toCharArray()) {
            hash = (31 * hash + c) % tableSize;
        }
        return hash;
    }
}
