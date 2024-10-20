
public class TextFileIndexerApp {
    public static void main(String[] args) {
        Tokenizer tokenizer = new SimpleTokenizer();  // Use simple tokenization
        FileIndexer fileIndexer = new FileIndexer(tokenizer);  // FileIndexer with the selected tokenizer
        ConsoleInterface consoleInterface = new ConsoleInterface(fileIndexer);

        consoleInterface.start();
    }
}
