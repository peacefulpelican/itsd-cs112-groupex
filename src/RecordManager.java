import java.util.ArrayList;

/**
 * Manages a collection of DisplayableRecord objects
 */

public class RecordManager {

    private ArrayList<DisplayableRecord> records;

    public RecordManager() {
        this.records = new ArrayList<>();
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
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId().equals(id)) {
                records.remove(i);
                return true;
            }
        }
        return false;
    }

    public ArrayList<DisplayableRecord> getAllRecords() {
        return new ArrayList<>(records);
    }

    public DisplayableRecord findRecordById(String id) {
        if (id == null || id.isBlank()) return null;
        for (DisplayableRecord record : records) {
            if (record.getId().equals(id)) {
                return record;
            }
        }

        return null;
    }

    public void setRecords(ArrayList<DisplayableRecord> records) {
        if (records == null) throw new IllegalArgumentException("Records cannot be null.");
        this.records.clear();
        this.records.addAll(records);
    }

    public void displayAllRecords() {
        for (DisplayableRecord record : records) {
            record.getDisplayText();
        }
    }
}