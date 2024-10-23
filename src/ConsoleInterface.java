import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.Set;

class ConsoleInterface implements IConsoleInterface {
    private final FileIndexer fileIndexer;
    private Path currentDirectory; // Track current working directory

    public ConsoleInterface(FileIndexer fileIndexer) {
        this.fileIndexer = fileIndexer;
        this.currentDirectory = Paths.get("C:\\"); // Default to C:\ directory
    }

    @Override
    public void start() {
        // Index predefined test files
        indexTestFiles();
        Boolean not_invalid= false;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Index a file");
            System.out.println("2. Index a directory");
            System.out.println("3. Query word in indexed files");
            System.out.println("4. Exit");

            System.out.println("Current directory: " + currentDirectory.toString());
            System.out.print("> ");

            String command = scanner.nextLine().trim();

            if (command.equalsIgnoreCase("show hidden index")) {
                showHiddenIndex();
                not_invalid= true;
                continue;
            }
            if (command.equalsIgnoreCase("explore") || command.equalsIgnoreCase("file explorer") || command.equalsIgnoreCase("explorer")) {
                openFileExplorer(currentDirectory.toString());
                not_invalid= true;
                continue;
            }
            if (command.startsWith("cd ")) {
                changeDirectory(command.substring(3).trim()); // Extract path after 'cd'
                not_invalid= true;
                continue;
            }

            if (command.equals("ls")) {
                listDirectoryContents();
                not_invalid= true;
                continue;
            }

            if(command.equalsIgnoreCase("index this")||
                    command.equalsIgnoreCase("this") ) {

                fileIndexer.indexDirectory(currentDirectory);
                not_invalid= true;

            }


            switch (command) {
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
                    } catch (InvalidPathException e) {
                        System.out.println("Error: The directory path contains invalid characters.");
                    }
                    break;
                case "3":
                    System.out.print("Enter word to query: ");
                    String word = scanner.nextLine();
                    Set<Path> files = fileIndexer.query(word);
                    if (files.isEmpty()) {
                        System.out.println("No files found containing the word '" + word + "'.");
                        System.out.println("Try smaller tokens or switch to advanced");
                    } else {
                        System.out.println("Files containing the word '" + word + "':");
                        files.forEach(System.out::println);
                        files.forEach(fileIndexer::openFileInNotepad);
                    }
                    break;
                case "4":
                    System.out.println("Exiting.");
                    return;
                default:
                    if(!not_invalid)
                      System.out.println("Invalid command.");

            }
        }
    }

    @Override
    public void changeDirectory(String path) {
        Path newPath;
        if (path.equals("..")) { // Go up one directory
            newPath = currentDirectory.getParent();
            if (newPath != null) {
                currentDirectory = newPath;
            }
        } else {
            newPath = currentDirectory.resolve(path).normalize();
            File dir = new File(newPath.toString());
            if (dir.exists() && dir.isDirectory()) {
                currentDirectory = newPath;
            } else {
                System.out.println("Directory not found: " + path);
            }
        }
    }

    public static final String BLUE = "\033[34m"; // ANSI code for blue

    public static void printColored(String message, String color) {
        System.out.print(color + message + "\033[0m"); // Reset color after printing
    }

    @Override
    public void listDirectoryContents() {
        File dir = new File(currentDirectory.toString());
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printColored("[DIR]  " + file.getName() + "\n", BLUE); // Print directories in blue
                } else {
                    System.out.println("       " + file.getName());
                }
            }
        } else {
            System.out.println("Error reading directory contents.");
        }
    }

    @Override
    public void indexTestFiles() {
        for (int i = 1; i <= 10; i++) {
            String fileName = "test" + i + ".txt";
            Path filePath = Paths.get(fileName);
            try {
                fileIndexer.indexFile(filePath);
                System.out.println("Indexed: " + fileName);
            } catch (IOException e) {
                System.out.println("Error indexing file " + fileName + ": " + e.getMessage());
            }
        }
    }

    @Override
    public void showHiddenIndex() {
        Set<Path> indexedFiles = fileIndexer.getAllIndexedFiles();
        if (indexedFiles.isEmpty()) {
            System.out.println("No files are indexed.");
        } else {
            System.out.println("Indexed files:");
            indexedFiles.forEach(System.out::println);
        }
    }

    @Override
    public void openFileExplorer(String directory) {
        try {
            Runtime.getRuntime().exec("explorer " + directory);
            System.out.println("Opening File Explorer at " + directory);
        } catch (IOException e) {
            System.out.println("Error opening File Explorer: " + e.getMessage());
        }
    }
}
