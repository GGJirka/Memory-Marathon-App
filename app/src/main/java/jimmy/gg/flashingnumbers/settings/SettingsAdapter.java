package jimmy.gg.flashingnumbers.settings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;

/**
 * Created by ggjimmy on 2/18/17.
 */

public class SettingsAdapter extends BaseAdapter {
    public final String LEVEL_KEY = "HIGHEST_LEVEL1";
    private Context context;
    private ArrayList<String> settings;
    private View v;

    public SettingsAdapter(Context context, ArrayList<String> settings) {
        this.context = context;
        this.settings = settings;
    }

    @Override
    public int getCount() {
        return settings.size();
    }

    @Override
    public Object getItem(int position) {
        return settings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = View.inflate(context, R.layout.activity_score, null);
        TextView setting = (TextView) v.findViewById(R.id.high_score_level);
        setting.setTextSize(17);
        setting.setText(settings.get(position));
        v.setTag(position);
        return v;
    }
}
