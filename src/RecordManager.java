import java.util.ArrayList;

public class RecordManager {
    private ArrayList<DisplayableRecord> records;

    public RecordManager() {
        records = new ArrayList<>();
    }

    public void addRecord(DisplayableRecord record) {
        if (records.contains(record)) throw new IllegalArgumentException("Record already exists");
        else records.add(record);
    }

    public boolean removeRecord(String id) {
        if (id == null) return false;
        return records.removeIf(r -> id.equals(r.getId()));
    }

    public ArrayList<DisplayableRecord> getAllRecords() { return records; }

    public DisplayableRecord findRecordById(String id) {
        for (DisplayableRecord r : records) {
            if (r.getId().equals(id)) return r;
        }
        return null;
    }

    public void setRecords(ArrayList<DisplayableRecord> records) {
        this.records.clear();
        this.records.addAll(records);
    }
    public void displayAllRecords() {
        records.forEach(System.out::println);
    }
}
