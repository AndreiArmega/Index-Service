import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    public void indexDirectory(Path directoryPath) throws IOException {
        Files.walk(directoryPath)
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        indexFile(filePath);
                    } catch (IOException e) {
                        System.out.println("Error indexing file: " + filePath);
                    }
                });
    }

    // Query for files that contain a specific word
    public Set<Path> query(String word) {
        return wordToFileMap.getOrDefault(word.toLowerCase(), Collections.emptySet());
    }
}