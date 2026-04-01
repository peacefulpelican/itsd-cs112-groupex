import java.util.Objects;

/**
 * Represents a single book record
 * Each instance is uniquely identified by an auto-generated ID derived from title, year and author.
 */
public class BookRecord implements DisplayableRecord {
    private String id;
    private String title;
    private String author;
    private int year;

    /**
     * Constructs new BookRecord object with supplied details.
     * Validates all details passed before assignment, and generates ID number.
     * @param title Title of book
     * @param author Author's name
     * @param year Publication year
     * @throws IllegalArgumentException where inputs fail validation
     */
    public BookRecord(String title, String author, int year) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        if (year < 0 || year > 2026) {
            throw new IllegalArgumentException("Year should be between 0 and 2026.");
        }

        this.title = title;
        this.author = author;
        this.year = year;
        generateID();
    }

    /**
     * Generate ID number for entry which is composed of the first 3 characters of the title,
     * the publication year and the first 3 characters of the author's name.
     * Should the author name or title be less than 3 characters, the ID will be shorter (no padding).
     */
    @Override
    public void generateID() {
        int authorLen = Math.min(author.length(), 3);
        int titleLen = Math.min(title.length(), 3);
        this.id = title.substring(0, titleLen) + year + author.substring(0, authorLen);
    }

    /**
     * Returns the auto-gennerated unique identifier for this record.
     * @return the record ID string.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Prints a formatted summary of the record to standard output (uses toString())
     */
    @Override
    public void getDisplayText() {
        System.out.println(this);
    }

    /**
     * Returns a string representation of this record.
     * @return string representation of record
     */
    @Override
    public String toString() {
        return getId() + " - " + title + " by " + author + " (" + year + ")";
    }

    /**
     * Returns a hash code based on the record ID, title and author
     * @return hashcode for record
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    /**
     * Compares this record to another for equality
     * @param obj   the reference object with which to compare.
     * @return true if the compared object's ID, title and author match. False otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof BookRecord br)) return false;
        return this.author.equals(br.author) && this.title.equals(br.title) && this.getId().equals(br.getId());
    }
}


