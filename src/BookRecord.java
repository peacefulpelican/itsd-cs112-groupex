import java.util.Objects;

public class BookRecord implements DisplayableRecord {
    private String id;
    private String title;
    private String author;
    private int year;

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
     * Generate ID number for entry
     * Should the author name or title be less than 3 characters, the ID will be shorter.
     */
    @Override
    public void generateID() {
        int authorLen = Math.min(author.length(), 3);
        int titleLen = Math.min(title.length(), 3);
        this.id = title.substring(0, titleLen) + year + author.substring(0, authorLen);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void getDisplayText() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return getId() + " - " + title + " by " + author + " (" + year + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof BookRecord br)) return false;
        return this.author.equals(br.author) && this.title.equals(br.title) && this.getId().equals(br.getId());
    }
}


