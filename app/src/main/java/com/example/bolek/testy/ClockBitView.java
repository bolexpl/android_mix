package com.example.bolek.testy;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class ClockBitView extends android.support.v7.widget.AppCompatTextView {

    public static final int INACTIVE = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;
    public static final int RED_BLUE = 3;

    private int state = R.integer.inactive;

    private Paint paint;
    private Paint paint2;
    private int width = -1;
    private int height = -1;

    public ClockBitView(Context context) {
        super(context);
        init(null, 0);
    }

    public ClockBitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ClockBitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ClockBitView, defStyle, 0);

        state = a.getInt(R.styleable.ClockBitView_state, state);

        a.recycle();

        paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        if (state == INACTIVE) {
            paint.setColor(getResources().getColor(R.color.widget_inactive));
        } else if (state == BLUE) {
            paint.setColor(getResources().getColor(R.color.blue));
        } else if (state == RED) {
            paint.setColor(getResources().getColor(R.color.red));
        } else {
            paint.setColor(getResources().getColor(R.color.blue));
            paint2 = new Paint();
            paint2.setFlags(Paint.ANTI_ALIAS_FLAG);
            paint2.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
            paint2.setColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (width == -1) {
            width = getWidth();
            height = getHeight();
        }

        canvas.drawRect(0, 0, width, height, paint);

        if (state == RED_BLUE) {
            canvas.drawCircle(width / 2, height / 2, 23, paint2);
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        invalidateTextPaintAndMeasurements();
    }
}
