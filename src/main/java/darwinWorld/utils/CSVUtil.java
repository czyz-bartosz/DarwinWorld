package darwinWorld.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class CSVUtil {
    private static String convertLineToCSV(Collection<String> line) {
        return line
                .stream()
                .map(CSVUtil::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private static String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static void appendToFile(String filePath, Collection<String> data) {
        File file = new File(filePath);
        String lineToAppend = convertLineToCSV(data);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(lineToAppend);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("An error occurred while appending to the file: " + e.getMessage());
        }

    }
}
