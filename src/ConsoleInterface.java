import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;

class ConsoleInterface {
    private final FileIndexer fileIndexer;

    public ConsoleInterface(FileIndexer fileIndexer) {
        this.fileIndexer = fileIndexer;
    }

    // Start the console interaction
    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Index a file");
            System.out.println("2. Index a directory");
            System.out.println("3. Query word in indexed files");
            System.out.println("4. Exit");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Enter file path: ");
                    String filePath = scanner.nextLine();
                    try {
                        fileIndexer.indexFile(Paths.get(filePath));
                        System.out.println("File indexed.");
                    } catch (IOException e) {
                        System.out.println("Error indexing file: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.print("Enter directory path: ");
                    String dirPath = scanner.nextLine();
                    try {
                        fileIndexer.indexDirectory(Paths.get(dirPath));
                        System.out.println("Directory indexed.");
                    } catch (IOException e) {
                        System.out.println("Error indexing directory: " + e.getMessage());
                    }
                    break;
                case "3":
                    System.out.print("Enter word to query: ");
                    String word = scanner.nextLine();
                    Set<Path> files = fileIndexer.query(word);
                    if (files.isEmpty()) {
                        System.out.println("No files found containing the word '" + word + "'.");
                    } else {
                        System.out.println("Files containing the word '" + word + "':");
                        files.forEach(System.out::println);
                    }
                    break;
                case "4":
                    System.out.println("Exiting.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}