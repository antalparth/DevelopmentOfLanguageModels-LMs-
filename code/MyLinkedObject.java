public class MyLinkedObject {

    private String word;
    private int count;
    private MyLinkedObject next;

    public MyLinkedObject(String w) {
        this.word = w;
        this.count = 1;
        this.next = null;
    }

    public void setWord(String w) {
        if (w.compareTo(word) < 0 || next == null) {
            MyLinkedObject newObj = new MyLinkedObject(w);
            newObj.next = this.next;
            this.next = newObj;
        } else if (w.equals(word)) {
            this.count++;
        } else {
            next.setWord(w);
        }
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public MyLinkedObject getNext() {
        return next;
    }

    public void incrementCount() {
        count++;
    }

    // Method to print the linked list starting from this node
    public void printList() {
        MyLinkedObject current = this;
        while (current != null) {
            System.out.println("Word: " + current.word + ", Count: " + current.count);
            current = current.next;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        MyLinkedObject current = this;
        while (current != null) {
            builder.append("{ Word: ").append(current.word).append(", Count: ").append(current.count).append(" } -> ");
            current = current.next;
        }
        builder.append("null");
        return builder.toString();
    }

}
