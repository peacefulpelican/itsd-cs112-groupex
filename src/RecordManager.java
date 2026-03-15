import java.util.ArrayList;

public class RecordManager {
    private ArrayList<DisplayableRecord> records;

    public RecordManager() {
        records = new ArrayList<>();
    }

    public void addRecord(DisplayableRecord record) {
        if (record == null) throw new IllegalArgumentException("Record cannot be null.");
        else if (records.contains(record)) throw new IllegalArgumentException("Record already exists");
        else {
            records.add(record);
            System.out.println("Record with ID" + record.getId() + " added successfully.");
        }
    }

    public boolean removeRecord(String id) {
        if (id == null || id.isBlank()) return false;
        return records.removeIf(r -> id.equals(r.getId()));
    }

    public ArrayList<DisplayableRecord> getAllRecords() { return new ArrayList<>(records); }

    public DisplayableRecord findRecordById(String id) {
        if (id == null || id.isBlank()) return null;
        return records.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void setRecords(ArrayList<DisplayableRecord> records) {
        if (records == null) throw new IllegalArgumentException("Records cannot be null.");
        this.records.clear();
        this.records.addAll(records);
    }

    public void displayAllRecords() {
        records.forEach(DisplayableRecord::getDisplayText);
    }
}
