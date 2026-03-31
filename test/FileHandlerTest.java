import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private FileHandler fileHandler;
    private Path tempFile;
    private Path tempReadableFile;
    private BookRecord book1;
    private BookRecord book2;
    private ArrayList<DisplayableRecord> records;

    @BeforeEach
    public void setUp() throws Exception {
        tempFile = Files.createTempFile("testRecords", ".ser");
        tempReadableFile = Files.createTempFile("testReadable", ".txt");
        fileHandler = new FileHandler(tempFile.toString());
        book1 = new BookRecord("Title1", "Author1", 2000);
        book2 = new BookRecord("Title2", "Author2", 2001);
        records = new ArrayList<>();
        records.add(book1);
        records.add(book2);
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(tempFile);
        Files.deleteIfExists(tempReadableFile);
    }

    @Test
    public void saveAndLoadRecordsTest() {
        fileHandler.saveRecords(records);
        ArrayList<DisplayableRecord> loaded = fileHandler.loadRecords();
        assertNotNull(loaded);
        assertEquals(2, loaded.size());
        assertEquals(book1, loaded.get(0));
        assertEquals(book2, loaded.get(1));
    }

    @Test
    public void loadRecordsNonExistentFileTest() {
        FileHandler fh2 = new FileHandler("nonexistent.ser");
        assertNull(fh2.loadRecords());
    }

    @Test
    public void exportReadableRecordsTest() {
        fileHandler.exportReadableRecords(records, tempReadableFile.toString());
        assertTrue(Files.exists(tempReadableFile));
    }

    @Test
    public void setFileNameTest() {
        fileHandler.setFileName("newFile.ser");
        assertEquals("newFile.ser", fileHandler.getFileName());
    }
}
