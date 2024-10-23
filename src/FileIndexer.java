import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

class FileIndexer {
    private final Map<String, Set<Path>> wordToFileMap;
    private final Tokenizer tokenizer;

    public FileIndexer(Tokenizer tokenizer) {
        this.wordToFileMap = new HashMap<>();
        this.tokenizer = tokenizer;
    }

    // Index a file and add its words to the map
    public void indexFile(Path filePath) throws IOException {
        String content = new String(Files.readAllBytes(filePath));
        List<String> tokens = tokenizer.tokenize(content);
        for (String token : tokens) {
            if (!token.isEmpty()) {
                wordToFileMap
                        .computeIfAbsent(token.toLowerCase(), k -> new HashSet<>())
                        .add(filePath);
            }
        }
    }

    // Index all files in a directory recursively
    public void indexDirectory(Path directoryPath) {
        int[] indexedFilesCount = {0};

        try {
            Files.walkFileTree(directoryPath, new HashSet<>(Arrays.asList(FileVisitOption.FOLLOW_LINKS)),
                    Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            try {
                                // Index only regular files
                                if (Files.isRegularFile(file)) {
                                    indexFile(file);
                                    indexedFilesCount[0]++;
                                }
                            } catch (IOException e) {
                                System.err.println("Error indexing file: " + file + " - " + e.getMessage());
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) {
                            if (exc instanceof AccessDeniedException) {
                                System.err.println("Access denied to file: " + file);
                            } else {
                                System.err.println("Failed to access file: " + file + " - " + exc.getMessage());
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                            System.out.println("Visiting directory: " + dir);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                            if (exc != null) {
                                System.err.println("Error visiting directory: " + dir + " - " + exc.getMessage());
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });

            System.out.println("Directory successfully indexed. Total files indexed: " + indexedFilesCount[0]);

        } catch (IOException e) {
            System.err.println("An error occurred while walking the directory tree: " + e.getMessage());
        } catch(OutOfMemoryError e) {
            System.err.println("Out of memory while walking the directory tree: " + e.getMessage());
        }
    }



    // Query for files that contain a specific word
    public Set<Path> query(String word) {
        return wordToFileMap.getOrDefault(word.toLowerCase(), Collections.emptySet());
    }

    // New method to retrieve all indexed files
    public Set<Path> getAllIndexedFiles() {
        Set<Path> indexedFiles = new HashSet<>();
        for (Set<Path> files : wordToFileMap.values()) {
            indexedFiles.addAll(files);
        }
        return indexedFiles;
    }

    // Add the method to open a file in Notepad
    public void openFileInNotepad(Path filePath) {
        try {
            new ProcessBuilder("notepad.exe", filePath.toString()).start();
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }
}
