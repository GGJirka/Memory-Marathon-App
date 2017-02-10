package jimmy.gg.flashingnumbers.helpers;

import android.app.Application;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;

/**
 * Created by ggjimmy on 2/10/17.
 */

public class IternalMemory extends Application {
    private ArrayList<Level> levelList;

    public IternalMemory(){
        levelList = new ArrayList<>();
        levelList.add(new Level("Level 1", "numbers: 5   ", "time: 10s   ", "easy"));
        levelList.add(new Level("Level 2", "numbers: 8   ", "time: 20s   ", "easy"));
        levelList.add(new Level("Level 3", "numbers: 13 ", "time: 45s   ", "easy"));
        levelList.add(new Level("Level 4", "numbers: 15 ", "time: 45s   ", "easy"));
        levelList.add(new Level("Level 5", "numbers: 19 ", "time: 60s   ", "easy"));
        levelList.add(new Level("Level 6", "numbers: 25 ", "time: 80s   ", "medium"));
        levelList.add(new Level("Level 7", "numbers: 30 ", "time: 80s   ", "medium"));
        levelList.add(new Level("Level 8", "numbers: 33 ", "time: 80s   ", "medium"));
        levelList.add(new Level("Level 9", "numbers: 40 ", "time: 120s", "medium"));
        levelList.add(new Level("Level 10", "numbers: 45 ", "time: 120s", "medium"));
        levelList.add(new Level("Level 11", "numbers: 55 ", "time: 150s", "hard"));
        levelList.add(new Level("Level 12", "numbers: 65 ", "time: 180s", "hard"));
        levelList.add(new Level("Level 13", "numbers: 80 ", "time: 180s", "hard"));
        levelList.add(new Level("Level 14", "numbers: 100", "time: 180s", "hard"));
    }
    public ArrayList<Level> getLevelList(){
        return this.levelList;
    }
}
