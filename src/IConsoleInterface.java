// IConsoleInterface.java
import java.nio.file.Path;
import java.util.Set;

public interface IConsoleInterface {
    void start();
    void changeDirectory(String path);
    void listDirectoryContents();
    void indexTestFiles();
    void showHiddenIndex();
    void openFileExplorer(String directory);
}
