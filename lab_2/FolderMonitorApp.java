package lab_2;

import java.io.*;
import java.nio.file.*;
import java.sql.Timestamp;
import java.util.*;

public class FolderMonitorApp {

    public static void main(String[] args) {
        AppLoop appLoop = new AppLoop();
        appLoop.run();
    }
}

// Class to handle application logic
class AppLoop {
    Scanner scanner;
    MainMenu mainMenu;
    SnapshotManagementSystem snapshotManagementSystem;

    public AppLoop() {
        this.scanner = new Scanner(System.in);
        this.mainMenu = new MainMenu(this.scanner);
        this.snapshotManagementSystem = new SnapshotManagementSystem();
    }

    public void run() {
        this.mainMenu.printMenu();
        boolean running = true;
        while (running) {
            this.mainMenu.printChoices();
            String input = this.mainMenu.takeUserInput();
            String[] commands = input.split(" ");
            switch (commands[0]) {
                case "c", "commit" -> {
                    this.snapshotManagementSystem.loadStateFromCurrSnapshot();
                    this.snapshotManagementSystem.commit();
                }
                case "s", "status" -> this.snapshotManagementSystem.printStatus();
                case "i", "info" -> {
                    if (commands.length > 1) {
                        String fileName = commands[1];
                        this.snapshotManagementSystem.informationAboutFile(fileName);
                    } else {
                        System.out.println("Please specify a file name.");
                    }
                }
                case "r", "revert" -> {
                    String snapshotTimestamp = commands.length > 1 ? commands[1] : null;
                    this.snapshotManagementSystem.revert(snapshotTimestamp);
                }
                case "h", "help" -> this.mainMenu.printHelp();
                case "q", "quit" -> {
                    this.mainMenu.printExit();
                    running = false;
                }
                default -> System.out.println("Invalid command. Type 'h' for help.");
            }
        }
        this.scanner.close();
    }

    // Menu Class
    class MainMenu {
        Scanner scanner;

        public MainMenu(Scanner scanner) {
            this.scanner = scanner;
        }

        public void printMenu() {
            System.out.println("Welcome to the Folder Monitor Application!");
        }

        public void printChoices() {
            System.out.println("""
                    Choose an option:
                    c | commit - Take a snapshot of the current folder state
                    s | status - Show changes since the last snapshot
                    i | info [filename] - Show details about a specific file
                    r | revert [snapshot_timestamp] - Revert folder state to a specific snapshot
                    h | help - Show this menu
                    q | quit - Exit the application
                    """);
        }

        public void printHelp() {
            printChoices();
        }

        public void printExit() {
            System.out.println("Exiting the application. Goodbye!");
        }

        public String takeUserInput() {
            System.out.print("Enter your command: ");
            return scanner.nextLine().trim();
        }
    }

    // Snapshot Management System
    class SnapshotManagementSystem {
        private final String filesDirectoryPath = "C:\\Users\\grigo\\Desktop\\oop_laboratory\\lab_2\\filesExample";
        private final String snapshotFilePath = "C:\\Users\\grigo\\Desktop\\oop_laboratory\\lab_2\\snapshot.txt";
        private Long lastSnapshotDate;
        private final HashMap<String, GeneralFile> currSnapshot = new HashMap<>();
        private final HashMap<String, GeneralFile> prevSnapshot = new HashMap<>();
        private final FilesGenerator filesGenerator = new FilesGenerator();

        public void saveNewSnapshotDate() {
            String snapshotDirPath = "C:\\Users\\grigo\\Desktop\\oop_laboratory\\lab_2\\snapshots";
            File snapshotDir = new File(snapshotDirPath, String.valueOf(lastSnapshotDate));
            
            if (!snapshotDir.exists() && !snapshotDir.mkdirs()) {
                System.out.println("Error creating snapshot directory.");
                return;
            }
        
            for (String fileName : prevSnapshot.keySet()) {
                GeneralFile file = prevSnapshot.get(fileName);
                File sourceFile = new File(filesDirectoryPath, fileName);
                File destinationFile = new File(snapshotDir, fileName);
                try {
                    Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Error saving file to snapshot: " + e.getMessage());
                }
            }
            System.out.println("Snapshot saved successfully at " + snapshotDir.getAbsolutePath());
        }
        

        public void revert(String snapshotTimestamp) {
            try {
                String snapshotsDirPath = "C:\\Users\\grigo\\Desktop\\oop_laboratory\\lab_2\\snapshot.txt";
                File snapshotsDir = new File(snapshotsDirPath);
        
                if (snapshotTimestamp == null || snapshotTimestamp.isEmpty()) {
                    File[] snapshotDirs = snapshotsDir.listFiles(File::isDirectory);
                    if (snapshotDirs != null) {
                        System.out.println("Available snapshots:");
                        for (File dir : snapshotDirs) {
                            System.out.println(dir.getName());
                        }
                    }
                    System.out.println("Usage: revert <snapshot_timestamp>");
                    return;
                }
        
                // Construct the snapshot directory path
                File snapshotDir = new File(snapshotsDir, snapshotTimestamp);
                if (!snapshotDir.exists() || !snapshotDir.isDirectory()) {
                    System.out.println("Snapshot not found: " + snapshotDir.getAbsolutePath());
                    System.out.println("Available snapshots:");
                    File[] snapshotDirs = snapshotsDir.listFiles(File::isDirectory);
                    if (snapshotDirs != null) {
                        for (File dir : snapshotDirs) {
                            System.out.println(dir.getName());
                        }
                    }
                    return;
                }
        
                // Restore the snapshot
                restoreSnapshot(snapshotDir);
                System.out.println("Reverted to snapshot: " + snapshotTimestamp);
            } catch (Exception ex) {
                System.out.println("An error occurred during revert: " + ex.getMessage());
            }
        }
        

        private void restoreSnapshot(File snapshotDir) {
            try {
                // Delete all current files in the monitored directory
                File monitoredDir = new File(filesDirectoryPath);
                File[] currentFiles = monitoredDir.listFiles();
                if (currentFiles != null) {
                    for (File file : currentFiles) {
                        if (file.isFile()) {
                            file.delete();
                        }
                    }
                }

                // Copy all files from the snapshot to the monitored directory
                File[] snapshotFiles = snapshotDir.listFiles();
                if (snapshotFiles != null) {
                    for (File snapshotFile : snapshotFiles) {
                        Path destPath = Paths.get(filesDirectoryPath, snapshotFile.getName());
                        Files.copy(snapshotFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error restoring snapshot: " + e.getMessage());
            }
        }

        public void loadStateFromPrevSnapshot() {
            try {
                List<String> lines = Files.readAllLines(Paths.get(snapshotFilePath));
                if (lines.isEmpty())
                    return;

                String snapshotTime = lines.get(0).split(": ID: ")[1];
                lastSnapshotDate = Long.parseLong(snapshotTime);
                prevSnapshot.clear();

                for (int i = 1; i < lines.size(); i++) {
                    String[] parts = lines.get(i).split(" - ");
                    String fileName = parts[0];
                    Long lastModified = Long.parseLong(parts[1]);
                    prevSnapshot.put(fileName,
                            filesGenerator.generateNewFile(filesDirectoryPath, fileName, lastModified));
                }
                System.out.println("Loaded previous snapshot.");
            } catch (IOException e) {
                System.out.println("Error loading snapshot: " + e.getMessage());
            }
        }

        public void loadStateFromCurrSnapshot() {
            currSnapshot.clear();
            File directory = new File(filesDirectoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("Invalid directory: " + filesDirectoryPath);
                return;
            }
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                currSnapshot.put(file.getName(),
                        filesGenerator.generateNewFile(filesDirectoryPath, file.getName(), file.lastModified()));
            }
        }

        public void commit() {
            prevSnapshot.clear();
            prevSnapshot.putAll(currSnapshot);
            lastSnapshotDate = System.currentTimeMillis();
            saveNewSnapshotDate();
            System.out.println("Snapshot updated at: " + new Timestamp(lastSnapshotDate));
        }

        public void printStatus() {
            loadStateFromPrevSnapshot();
            loadStateFromCurrSnapshot();

            for (String fileName : prevSnapshot.keySet()) {
                if (!currSnapshot.containsKey(fileName)) {
                    System.out.println(fileName + " - Deleted");
                }
            }

            for (String fileName : currSnapshot.keySet()) {
                GeneralFile currentFile = currSnapshot.get(fileName);
                GeneralFile previousFile = prevSnapshot.get(fileName);

                if (!prevSnapshot.containsKey(fileName)) {
                    System.out.println(fileName + " - New File");
                } else if (!currentFile.getLastModificationDate().equals(previousFile.getLastModificationDate())) {
                    System.out.println(fileName + " - Modified");
                } else {
                    System.out.println(fileName + " - No Changes");
                }
            }
        }

        public void informationAboutFile(String fileName) {
            if (currSnapshot.containsKey(fileName)) {
                System.out.println(currSnapshot.get(fileName));
            } else {
                System.out.println("File not found: " + fileName);
            }
        }
    }

    // General File Classes
    abstract class GeneralFile {
        protected String directoryPath;
        protected String fileName;
        protected Long lastModificationDate;

        public GeneralFile(String directoryPath, String fileName, Long lastModificationDate) {
            this.directoryPath = directoryPath;
            this.fileName = fileName;
            this.lastModificationDate = lastModificationDate;
        }

        public Long getLastModificationDate() {
            return lastModificationDate;
        }

        @Override
        public String toString() {
            return "File: " + fileName + ", Last Modified: " + new Timestamp(lastModificationDate);
        }
    }

    class TxtFile extends GeneralFile {
        public TxtFile(String directoryPath, String fileName, Long lastModificationDate) {
            super(directoryPath, fileName, lastModificationDate);
        }
    }

    class PngJpgFile extends GeneralFile {
        public PngJpgFile(String directoryPath, String fileName, Long lastModificationDate) {
            super(directoryPath, fileName, lastModificationDate);
        }
    }

    class PyJavaFile extends GeneralFile {
        public PyJavaFile(String directoryPath, String fileName, Long lastModificationDate) {
            super(directoryPath, fileName, lastModificationDate);
        }
    }

    class FilesGenerator {
        public GeneralFile generateNewFile(String directoryPath, String fileName, Long lastModificationDate) {
            String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
            return switch (extension) {
                case "txt" -> new TxtFile(directoryPath, fileName, lastModificationDate);
                case "jpg", "png" -> new PngJpgFile(directoryPath, fileName, lastModificationDate);
                case "py", "java" -> new PyJavaFile(directoryPath, fileName, lastModificationDate);
                default -> null;
            };
        }
    }
}