import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileHandler {
    private String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void saveRecords(ArrayList<DisplayableRecord> records) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Path.of(fileName)))) {
            oos.writeObject(records);
            System.out.println("Records saved to " + fileName);
        }
        catch (IOException e) {
            System.err.println("Error saving records!");
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<DisplayableRecord> loadRecords() {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Path.of(fileName)))) {
            System.out.println("Records loaded.");
            return (ArrayList<DisplayableRecord>) ois.readObject();
        } catch (IOException e) {
            System.err.println("Error reading records!");
            return null;
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading records, invalid format.");
            return null;
        }
    }
    public void exportReadableRecords(ArrayList<DisplayableRecord> records, String readableFileName) {
        try(BufferedWriter bf = Files.newBufferedWriter(Path.of(readableFileName), StandardCharsets.UTF_8)) {
            for (DisplayableRecord record : records) {
                bf.write(record.toString());
                bf.newLine();
            }
            System.out.println("Record saved to " + readableFileName);
        }
        catch (IOException e) {
            System.err.println("Error exporting records!");
        }
    }
}