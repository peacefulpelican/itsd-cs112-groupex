import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookRecordTest {

    @Test
    public void constructorValidTest() {
        BookRecord book = new BookRecord("The Hobbit", "J.R.R. Tolkien", 1937);
        assertEquals("The1937J.R", book.getId());
        // regex first splits the id and info and then splits info into the  author and remain info (Book Name and year)
        assertEquals("The Hobbit", book.toString().split(" - ")[1].split(" by ")[0]);
    }

    @Test
    public void constructorInvalidTitleTest() {
        assertThrows(IllegalArgumentException.class, () -> new BookRecord("", "Author", 2000));
        assertThrows(IllegalArgumentException.class, () -> new BookRecord(null, "Author", 2000));
    }

    @Test
    public void constructorInvalidAuthorTest() {
        assertThrows(IllegalArgumentException.class, () -> new BookRecord("Title", "", 2000));
        assertThrows(IllegalArgumentException.class, () -> new BookRecord("Title", null, 2000));
    }

    @Test
    public void constructorInvalidYearTest() {
        assertThrows(IllegalArgumentException.class, () -> new BookRecord("Title", "Author", -1));
        assertThrows(IllegalArgumentException.class, () -> new BookRecord("Title", "Author", 2027));
    }

    @Test
    public void generateIDTest() {
        BookRecord book = new BookRecord("Before", "After", 2020);
        assertEquals("Bef2020Aft", book.getId());
    }

    @Test
    public void toStringTest() {
        BookRecord book = new BookRecord("The Hobbit", "J.R.R. Tolkien", 1937);
        assertEquals("The1937J.R - The Hobbit by J.R.R. Tolkien (1937)", book.toString());
    }

    @Test
    public void equalsTest() {
        BookRecord book1 = new BookRecord("Title", "Author", 2000);
        BookRecord book2 = new BookRecord("Title", "Author", 2000);
        BookRecord book3 = new BookRecord("Different", "Author", 2000);
        assertEquals(book1, book2);
        assertNotEquals(book1, book3);
        assertNotEquals(book1, null);
        assertNotEquals(book1, "string");
    }
}
