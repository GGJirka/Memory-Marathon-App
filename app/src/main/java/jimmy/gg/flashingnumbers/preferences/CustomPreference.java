package jimmy.gg.flashingnumbers.preferences;


import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jimmy.gg.flashingnumbers.R;


public class CustomPreference extends PreferenceCategory {

    public CustomPreference(Context context) {
        super(context);
    }
    public CustomPreference(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public CustomPreference(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    @Override
    protected View onCreateView(ViewGroup group){
        TextView pref = (TextView) super.onCreateView(group);
        pref.setTextColor(Color.BLACK);
        pref.setBackgroundResource(R.color.gray);
        pref.setTextSize(15);
        return pref;
    }
}
