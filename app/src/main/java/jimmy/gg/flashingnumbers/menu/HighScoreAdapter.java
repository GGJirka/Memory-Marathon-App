package jimmy.gg.flashingnumbers.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;
import jimmy.gg.flashingnumbers.R;

/**
 * Created by root on 1/18/17.
 */

public class HighScoreAdapter extends BaseAdapter{
    public final static String LEVEL_KEY = "HIGHEST_LEVEL1";
    private Context context;
    private ArrayList<Score> levels;
    private View v;

    public HighScoreAdapter(Context context, ArrayList<Score> levels) {
        this.context = context;
        this.levels = levels;
    }

    @Override
    public int getCount() {
        return levels.size();
    }

    @Override
    public Object getItem(int position) {
        return levels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = View.inflate(context, R.layout.activity_score,null);
        TextView level = (TextView) v.findViewById(R.id.high_score_score);
        TextView numbers = (TextView) v.findViewById(R.id.high_score_level);
        numbers.setText(levels.get(position).getLevel());
        level.setText(levels.get(position).getHighScore());
        v.setTag(position);
        return v;
    }
}
