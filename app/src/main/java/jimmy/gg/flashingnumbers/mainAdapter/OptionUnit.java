package jimmy.gg.flashingnumbers.mainAdapter;

/**
 * Created by ggjimmy on 2/21/17.
 */

public class OptionUnit {
    public String id;
    public String text;

    public OptionUnit(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
