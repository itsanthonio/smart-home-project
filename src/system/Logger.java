package system;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Logger for the smart home system.
 * Logs events both to console and to a file.
 */
public class Logger {
    private final List<String> logs = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String logFilePath = "smart_home_log.txt";
    
    public Logger() {
        // Initialize the log file
        try {
            Files.createDirectories(Paths.get("logs"));
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/" + logFilePath, false))) {
                writer.write("===== SMART HOME LOG STARTED AT " + 
                           LocalDateTime.now().format(formatter) + " =====\n");
            }
        } catch (IOException e) {
            System.err.println("Error initializing log file: " + e.getMessage());
        }
    }
    
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + message;
        logs.add(logEntry);
        System.out.println(logEntry);
        
        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/" + logFilePath, true))) {
            writer.write(logEntry + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
    
    public List<String> getLogs() {
        return logs;
    }
    
    public void clearLogs() {
        logs.clear();
        
        // Clear file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("logs/" + logFilePath, false))) {
            writer.write("===== LOGS CLEARED AT " + 
                       LocalDateTime.now().format(formatter) + " =====\n");
        } catch (IOException e) {
            System.err.println("Error clearing log file: " + e.getMessage());
        }
    }
    
    public void printAllLogs() {
        System.out.println("===== SMART HOME LOG =====");
        for (String log : logs) {
            System.out.println(log);
        }
        System.out.println("=========================");
    }
    
    public String getLogFilePath() {
        return "logs/" + logFilePath;
    }
    
    public void readLogsFromFile() {
        try {
            System.out.println("Reading logs from file: " + getLogFilePath());
            List<String> fileLines = Files.readAllLines(Paths.get("logs/" + logFilePath));
            for (String line : fileLines) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }
}