import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Handles file I/O for DisplayableRecord collections using serialization or plain text export.
 * Target filename must be set before calling I/O methods.
 */
public class FileHandler {
    private String fileName;

    /**
     * Construct FileHandler using given fileName
     * @param fileName fileName for I/O operations
     */
    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Set the fileName to be used for I/O operations
     * @param fileName filename to be used for I/O operations
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Returns the current target filename
     * @return target filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Save the specified records list to a binary file using serialization
     * @param records record list to be saved.
     */
    public void saveRecords(ArrayList<DisplayableRecord> records) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Path.of(fileName)))) {
            oos.writeObject(records);
            System.out.println("Records saved to " + fileName);
        }
        catch (IOException e) {
            System.err.println("Error saving records!");
        }
    }

    /**
     * Load records stored in binary format using de-serialization.
     * @return de-serialized list of records or null on failure
     * Suppressed unchecked cast record because it was annoying.
     */
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

    /**
     * Export the specified list of records to a human-readable plain text file.
     * uses UTF-8 encoding for cross-platform compatibility.
     * @param records list of records to be exported
     * @param readableFileName destination file name for export
     */
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