package jimmy.gg.flashingnumbers.LevelManager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;

/**
 * Created by root on 1/18/17.
 */

public class LevelAdapter extends BaseAdapter{
    public final static String LEVEL_KEY = "HIGHEST_LEVEL1";
    private Context context;
    private ArrayList<Level> levels;
    private View v;

    public LevelAdapter(Context context, ArrayList<Level> levels) {
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
        v = View.inflate(context, R.layout.activity_level,null);
        TextView level = (TextView) v.findViewById(R.id.level);
        TextView numbers = (TextView) v.findViewById(R.id.numbers);
        TextView time = (TextView) v.findViewById(R.id.time);
        level.setText(levels.get(position).getLevel());
        numbers.setText(levels.get(position).getNumbers());
        time.setText(levels.get(position).getTime());

        switch(levels.get(position).getDifficult()){
            case "easy":
                v.setBackgroundResource(R.drawable.lw_easy);
                break;
            case "medium":
                v.setBackgroundResource(R.drawable.lw_medium);
                break;
            case "hard":
                v.setBackgroundResource(R.drawable.lw_hard);
                break;
            case "extreme":
                break;
            case "locked":
                v.setBackgroundResource(R.drawable.lw_locked);
                break;
        }
        v.setTag(position);
        return v;
    }
}
