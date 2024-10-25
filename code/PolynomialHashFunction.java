public class PolynomialHashFunction extends MyHashFunction {
    private int a;

    public PolynomialHashFunction(int size, int a) {
        super(size);
        this.a = a;
    }

    @Override
    public int hash(String word) {
        int hashValue = 0;
        for (int i = 0; i < word.length(); i++) {
            hashValue = (a * hashValue + word.charAt(i)) % tableSize;
        }
        return hashValue;
    }
}
