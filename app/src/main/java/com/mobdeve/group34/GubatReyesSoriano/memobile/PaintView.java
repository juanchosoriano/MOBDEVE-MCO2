package com.mobdeve.group34.GubatReyesSoriano.memobile;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class PaintView extends View {

    //public LayoutParams params;
    private Path path = new Path();
    private Paint brush = new Paint();

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //v.setBackgroundColor(0xFF00FF00);
//        View v = new View(context);
//        v.setBackgroundColor(Color.TRANSPARENT);
       this.setBackgroundColor(Color.TRANSPARENT);
        brush.setAntiAlias(true);
        brush.setColor(Color.YELLOW);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8f);


        //params = new LayoutParams(100, 300);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setBackgroundColor(Color.TRANSPARENT);
        setMeasuredDimension(1050, 1880);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float pointX = event.getX();
        float pointY = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(pointX, pointY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(pointX, pointY);
                break;
            default:
                return false;
        }
        postInvalidate();
        return false;
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(path, brush);
    }


}
