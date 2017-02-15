package jimmy.gg.flashingnumbers.words.words;

import android.support.v7.app.AppCompatActivity;


/**
 * Created by ggjimmy on 2/15/17.
 */

public class WordsHelper extends AppCompatActivity {

    private boolean state = false;

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }
}
