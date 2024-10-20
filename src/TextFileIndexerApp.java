import java.util.Scanner;

public class TextFileIndexerApp {
    public static void main(String[] args) {
        // Scanner to get user input for selecting tokenization system
        Scanner scanner = new Scanner(System.in);

        // Ask the user to choose between simple and advanced tokenization
        System.out.println("Choose tokenization system:");
        System.out.println("1. Simple Tokenizer (splits by words)");
        System.out.println("2. Advanced Tokenizer (lexer-based)");

        int choice = scanner.nextInt();

        // Declare tokenizer variable
        Tokenizer tokenizer;

        // Instantiate the appropriate tokenizer based on user choice
        if (choice == 1) {
            tokenizer = new SimpleTokenizer();  // Simple word-splitting tokenizer
            System.out.println("Using Simple Tokenizer...");
        } else if (choice == 2) {
            tokenizer = new AdvancedTokenizer();  // Advanced lexer-based tokenizer
            System.out.println("Using Advanced Tokenizer...");
        } else {
            System.out.println("Invalid choice, defaulting to Simple Tokenizer...");
            tokenizer = new SimpleTokenizer();
        }

        // Create FileIndexer with the chosen tokenizer
        FileIndexer fileIndexer = new FileIndexer(tokenizer);
        // Start the console interface to allow file indexing and querying
        ConsoleInterface consoleInterface = new ConsoleInterface(fileIndexer);
        consoleInterface.indexTestFiles();
        consoleInterface.start();
    }
}
