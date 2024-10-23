import java.util.Scanner;

public class TextFileIndexerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose tokenization system:");
        System.out.println("1. Simple Tokenizer (splits by words)");
        System.out.println("2. Advanced Tokenizer (lexer-based)");

        int choice = scanner.nextInt();
        Tokenizer tokenizer;
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

        FileIndexer fileIndexer = new FileIndexer(tokenizer);
        ConsoleInterface consoleInterface = new ConsoleInterface(fileIndexer);
        consoleInterface.indexTestFiles();
        consoleInterface.start();
    }
}
