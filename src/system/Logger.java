package system;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Logger for the smart home system.
 */
public class Logger {
    private final List<String> logs = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logEntry = "[" + timestamp + "] " + message;
        logs.add(logEntry);
        System.out.println(logEntry);
    }
    
    public List<String> getLogs() {
        return logs;
    }
    
    public void clearLogs() {
        logs.clear();
    }
    
    public void printAllLogs() {
        System.out.println("===== SMART HOME LOG =====");
        for (String log : logs) {
            System.out.println(log);
        }
        System.out.println("=========================");
    }
}