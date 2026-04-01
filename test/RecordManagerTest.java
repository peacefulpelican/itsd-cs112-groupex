import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class RecordManagerTest {

    private RecordManager recordManager;
    private BookRecord book1;
    private BookRecord book2;

    @BeforeEach
    public void setUp() {
        recordManager = new RecordManager();
        book1 = new BookRecord("Title1", "Author1", 2000);
        book2 = new BookRecord("Title2", "Author2", 2001);
    }

    @Test
    public void addRecordTest() {
        recordManager.addRecord(book1);
        assertEquals(1, recordManager.getAllRecords().size());
        assertThrows(IllegalArgumentException.class, () -> recordManager.addRecord(book1));
        assertThrows(IllegalArgumentException.class, () -> recordManager.addRecord(null));
    }

    @Test
    public void removeRecordTest() {
        recordManager.addRecord(book1);
        assertTrue(recordManager.removeRecord(book1.getId()));
        assertFalse(recordManager.removeRecord("nonexistent"));
        assertFalse(recordManager.removeRecord(null));
        assertFalse(recordManager.removeRecord(""));
    }

    @Test
    public void getAllRecordsTest() {
        recordManager.addRecord(book1);
        recordManager.addRecord(book2);
        ArrayList<DisplayableRecord> records = recordManager.getAllRecords();
        assertEquals(2, records.size());
        assertTrue(records.contains(book1));
        assertTrue(records.contains(book2));
    }

    @Test
    public void findRecordByIdTest() {
        recordManager.addRecord(book1);
        assertEquals(book1, recordManager.findRecordById(book1.getId()));
        assertNull(recordManager.findRecordById("nonexistent"));
        assertNull(recordManager.findRecordById(null));
        assertNull(recordManager.findRecordById(""));
    }

    @Test
    public void setRecordsTest() {
        ArrayList<DisplayableRecord> newRecords = new ArrayList<>();
        newRecords.add(book1);
        recordManager.setRecords(newRecords);
        assertEquals(1, recordManager.getAllRecords().size());
        assertThrows(IllegalArgumentException.class, () -> recordManager.setRecords(null));
    }

    @Test
    public void displayAllRecordsTest() {
        // Captures console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            recordManager.addRecord(book1);
            recordManager.displayAllRecords();
            // Restore original System.out
            System.setOut(originalOut);
            
            // Verify output contains expected record
            String output = outputStream.toString().trim();
            assertTrue(output.contains(book1.toString()));
        } finally {
            // Ensure System.out is restored even if test fails
            System.setOut(originalOut);
        }
    }
}
