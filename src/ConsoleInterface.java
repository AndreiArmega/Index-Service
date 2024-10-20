import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class ConsoleInterface {
    private final FileIndexer fileIndexer;

    public ConsoleInterface(FileIndexer fileIndexer) {
        this.fileIndexer = fileIndexer;
    }

    // Start the console interaction
    public void start() {
        // Index predefined test files
        indexTestFiles();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Index a file");
            System.out.println("2. Index a directory");
            System.out.println("3. Query word in indexed files");
            System.out.println("4. Exit");

            String option = scanner.nextLine();

            // Check for the hidden command
            if (option.equalsIgnoreCase("show hidden index")) {
                showHiddenIndex();
                continue; // Skip the menu options and go to the next iteration
            }

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
                        Path directoryPath = Paths.get(dirPath);
                        fileIndexer.indexDirectory(directoryPath);
                        System.out.println("Directory indexed.");
                    } catch (java.nio.file.InvalidPathException e) {
                        System.out.println("Error: The directory path contains invalid characters.");
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
                        // Open all files containing the word
                        files.forEach(fileIndexer::openFileInNotepad);
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

    // Method to index test files
    public void indexTestFiles() {
        for (int i = 1; i <= 10; i++) {
            String fileName = "test" + i + ".txt"; // Change this if your files are located in a different directory
            Path filePath = Paths.get(fileName);
            try {
                fileIndexer.indexFile(filePath);
                System.out.println("Indexed: " + fileName);
            } catch (IOException e) {
                System.out.println("Error indexing file " + fileName + ": " + e.getMessage());
            }
        }
    }

    // New method to show all indexed files
    private void showHiddenIndex() {
        Set<Path> indexedFiles = fileIndexer.getAllIndexedFiles();
        if (indexedFiles.isEmpty()) {
            System.out.println("No files are indexed.");
        } else {
            System.out.println("Indexed files:");
            indexedFiles.forEach(System.out::println);
        }
    }
}
