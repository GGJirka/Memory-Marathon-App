package jimmy.gg.flashingnumbers.mainAdapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;

/**
 * Created by ggjimmy on 2/21/17.
 */

public class OptionAdapter extends BaseAdapter {
    public ArrayList<OptionUnit> options;
    public Context context;

    public OptionAdapter(Context context, ArrayList<OptionUnit> options) {
        this.context = context;
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView.inflate(context, R.layout.option_adapter, null);
        TextView text = (TextView) v.findViewById(R.id.option_text);
        ImageView image = (ImageView) v.findViewById(R.id.option_icon);
        text.setText(options.get(position).getText());
        switch (options.get(position).getId()) {
            case "numbers":
                image.setImageResource(R.drawable.main_numbers_icon);
                break;
            case "words":
                image.setImageResource(R.drawable.words_icon);
                break;
            case "settings":
                image.setImageResource(R.drawable.settings);
                break;
        }
        v.setTag(position);
        return v;
    }
}
