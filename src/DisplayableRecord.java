import java.io.Serializable;

public interface DisplayableRecord extends Serializable {
    String getId();
    void generateID();
    void getDisplayText();
}