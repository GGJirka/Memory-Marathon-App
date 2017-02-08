package jimmy.gg.flashingnumbers.helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

import java.text.AttributedCharacterIterator;

/**
 * Created by ggjimmy on 2/8/17.
 */

public class CustomEditText extends EditText{
    private Rect rect;
    private Paint paint;

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        rect = new Rect();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0x800000FF);
    }

    @Override
    public void onDraw(Canvas canvas){
        Rect r = rect;
        Paint p = paint;
        int lines = getLineCount();
        for(int i=0;i<lines;i++){
            int customLine = getLineBounds(i,r);
            canvas.drawLine(r.left,customLine+5,r.right,customLine+5,paint);
            canvas.drawLine(r.left,customLine+6,r.right,customLine+6,paint);
            canvas.drawLine(r.left,customLine+7,r.right,customLine+7,paint);
            canvas.drawLine(r.left,customLine+8,r.right,customLine+8,paint);
            canvas.drawLine(r.left,customLine+9,r.right,customLine+9,paint);
            canvas.drawLine(r.left,customLine+10,r.right,customLine+10,paint);
        }
        super.onDraw(canvas);
    }
}
