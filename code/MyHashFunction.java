public abstract class MyHashFunction {
    protected int tableSize;

    public MyHashFunction(int tableSize) {
        this.tableSize = tableSize;
    }

    public abstract int hash(String word);

}
