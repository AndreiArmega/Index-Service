## Java Application for Indexing and Searching Text Files

This application provides a **console-based service** that indexes text files and directories, allowing efficient search queries. It features a user-friendly interface with **tokenization systems** and **file navigation options** that simplify file indexing and searching.

#### Interface Overview

Upon starting the application, the user is prompted to choose a tokenization system:
```
Choose tokenization system:
1. Simple Tokenizer (splits by words) 
2. Advanced Tokenizer (lexer-based)`
```
- **Simple Tokenizer**: Performs basic tokenization by splitting words based on whitespace and punctuation, suitable for general text searches.
- **Advanced Tokenizer**: A more sophisticated lexer-based tokenizer that supports searching for combinations of letters, symbols, and special characters.

#### Main Menu

After selecting a tokenizer, the user can choose from the following options:

```
Choose an option: 
1. Index a file 
2. Index a directory 
3. Query word in indexed files 
4. Exit
5. Current directory: C:\
6. >
```

- **1. Index a File**: Prompts the user to input a file path, which will index the file’s content, making it searchable.
- **2. Index a Directory**: Asks the user for a directory path, indexing all regular files in that directory.
- **3. Query Word in Indexed Files**: Allows the user to input a word (or token) to search across all previously indexed files.
- **4. Exit**: Closes the application.

The console also incorporates a **Linux-like file navigation system**:

- `cd [directory]`: Allows the user to change directories, simulating the `cd` command.
- `ls`: Lists the contents of the current directory, displaying files and subdirectories.
- The current directory is always displayed to keep the user aware of the location where commands will be executed.

#### Additional Commands

The application provides several shortcut commands for improved convenience:

- **show hidden index**: Displays all currently indexed files.
- **explore**/**file explorer**: Opens the current directory in the Windows file explorer.
- **index this**/**this**: Indexes all files in the current directory without needing to specify the directory name.

#### Example Output:

When navigating and using commands, the console interface might look like this:

```
Choose an option:
1. Index a file 
2. Index a directory 
3. Query word in indexed files 
4. Exit Current directory: C:\Documents\ 
>index this 
Directory successfully indexed. 
query important 
Files containing the word 'important': 
C:\Documents\file1.txt 
C:\Documents\notes.txt
````

#### Successful Query

- When a query is successful, the application opens all relevant files in Notepad, making it easy for the user to view the results.

#### Tokenization Systems

- **Simple Tokenizer**: A straightforward tokenizer that breaks the content by words and spaces, making it ideal for general text searches.
- **Advanced Tokenizer**: Uses a more advanced lexing approach, allowing for complex queries involving symbols, punctuation, and alphanumeric combinations (e.g., "secret@123!" or "error404").

#### Limitations and Considerations

- **Very large directories**: The application may encounter performance issues or run out of memory if attempting to index an extremely large directory.
- **Access Denied Errors**: Some directories, particularly system directories or protected areas like `C:\$Recycle.Bin`, may not be accessible due to permission restrictions. These errors will be handled gracefully by skipping those files.
- **Tokenization Edge Cases**: In some cases, tokens containing symbols or complex character combinations might not be captured perfectly by the simple tokenizer; for such cases, the advanced tokenizer is recommended.
- **No state persistence**: The application does not store indexed data between sessions. However, multiple queries within a single session are efficiently handled.

#### Testing and Usage Notes

- **Pre-indexed Files**: On startup, the application indexes 10 test text files to ensure immediate functionality and provide sample data for search queries.
- **Test Queries**: The following queries can be tested for different tokenization behaviors:
    - `Secret!word`
    - `SecretWord`
    - `##secret`
    - `#$^#^$^)!&@)*!&(@&`
- **Directory Indexing**: Indexing is performed quickly and efficiently for small to medium directories, and permission errors are handled gracefully.
- **Multi-file Queries**: Users can query words across multiple indexed files, with results displayed in the console and opened in Notepad for further inspection.
