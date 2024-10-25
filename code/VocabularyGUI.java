import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VocabularyGUI extends JFrame {
    private JTable nGramTable;
    private JTable wordTable;
    private MyHashTable hashTable; // For unigrams
    private JScrollPane scrollPane;
    private NGramModel nGramModel;

    public VocabularyGUI() {
        // Set up the frame
        setTitle("Vocabulary List");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the hash table with a polynomial hash function
        hashTable = new MyHashTable(997, new PolynomialHashFunction(997, 31));

        // Set up the word table
        wordTable = new JTable();
        scrollPane = new JScrollPane(wordTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Initialize nGramTable
        nGramTable = new JTable();
        JScrollPane nGramScrollPane = new JScrollPane(nGramTable);
        getContentPane().add(nGramScrollPane, BorderLayout.EAST); // Adjust layout as necessary

        // Use a file chooser to load the file
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt", "text");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            loadAndProcessFile(file.getAbsolutePath());
        }
    }

    private void loadAndProcessFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            scanner.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + filePath, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayVocabularyList() {
        List<MyLinkedObject> wordsList = hashTable.getAllWords();
        String[] columnNames = { "Word", "Frequency" };
        String[][] data = new String[wordsList.size()][2];

        for (int i = 0; i < wordsList.size(); i++) {
            MyLinkedObject wordEntry = wordsList.get(i);
            data[i][0] = wordEntry.getWord();
            data[i][1] = String.valueOf(wordEntry.getCount());
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false; // Make the table non-editable
            }
        };

        wordTable.setModel(model);
    }

    public void setHashTable(MyHashTable myHashTable) {
        this.hashTable = myHashTable;
        displayVocabularyList(); // Refresh the vocabulary list display
    }

    public void setNGramModel(NGramModel nGramModel) {
        this.nGramModel = nGramModel;
        displayNGramProbabilities();
    }

    public void displayNGramProbabilities() {
        // Assuming nGramModel is an instance of NGramModel and is already set
        Map<String, Double> bigramProbs = nGramModel.getBigramProbabilities();

        // Column names for the table
        String[] columnNames = { "Bigram", "Probability" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Adding rows to the model
        for (Map.Entry<String, Double> entry : bigramProbs.entrySet()) {
            model.addRow(new Object[] { entry.getKey(), entry.getValue() });
        }

        // Setting the model to the table and refreshing
        nGramTable.setModel(model);
        nGramTable.revalidate();
        nGramTable.repaint();
    }

}