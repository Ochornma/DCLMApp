package org.dclm.live.ui.jotter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;


public class MyEditText extends AppCompatEditText {
    private Rect mRect;
    private Paint mPaint;
    private Context context;



    public MyEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
        mRect = new Rect();
        mPaint = new Paint();
        // define the style of line
        mPaint.setStyle(Paint.Style.STROKE);
        // define the color of line
        mPaint.setColor(Color.BLACK);
    }

    public MyEditText(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int lHeight = getLineHeight();
        int left = getLeft();
        int right = getRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        //int count = (height-paddingTop-paddingBottom);
        // the number of line
        int count = height / lHeight;
        if (getLineCount() > count) {
            // for long text with scrolling
            count = getLineCount();
        }
        Rect r = mRect;
        Paint paint = mPaint;

        // first line
        int baseline = getLineBounds(0, r);

        // draw the remaining lines.
        for (int i = 0; i < count; i++) {
            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            // next line
            baseline += getLineHeight();

        }



/*
        for (int i = 0; i < count*2; i++) {
            int baseline = lHeight * (i+1) + paddingTop;

            canvas.drawLine(left+paddingLeft, baseline, right-paddingRight, baseline, mPaint);
        }*/
        super.onDraw(canvas);

    }

    public int number(){
        return getLineCount();
    }

}
