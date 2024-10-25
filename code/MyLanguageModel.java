import javax.swing.SwingUtilities;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyLanguageModel {

    public static void main(String[] args) throws FileNotFoundException {
        // Initialize the NGramModel
        NGramModel nGramModel = new NGramModel();

        // Process the text to populate the NGramModel
        nGramModel.processText("./news.txt");

        // Display the GUI
        SwingUtilities.invokeLater(() -> {
            VocabularyGUI gui = new VocabularyGUI();
            gui.setHashTable(nGramModel.getUnigramTable());
            gui.setNGramModel(nGramModel);
            gui.setVisible(true);
        });

        // Interactive prompt for user input to predict the next word
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Enter a word or sequence of words for prediction:  ");
            String context = sc.nextLine();
            String prediction = nGramModel.predictNextWords(context, 20);
            System.out.println("The next word prediction is: " + prediction);
        } finally {
            sc.close();
        }
    }
}
