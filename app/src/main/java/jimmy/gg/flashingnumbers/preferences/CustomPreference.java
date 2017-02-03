package jimmy.gg.flashingnumbers.preferences;


import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        group.setForegroundGravity(Gravity.CENTER_VERTICAL);
        TextView pref = (TextView) super.onCreateView(group);
        pref.setGravity(Gravity.CENTER_VERTICAL);
        pref.setTextColor(Color.BLACK);
        View v = super.onCreateView(group);
        v.setForegroundGravity(Gravity.CENTER_VERTICAL);
        pref.setBackgroundResource(R.color.gray);
        pref.setTextSize(15);
        return pref;
    }
}
