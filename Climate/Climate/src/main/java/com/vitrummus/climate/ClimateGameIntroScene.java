package com.vitrummus.climate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ClimateGameIntroScene extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = ClimateGameIntroScene.class.getSimpleName();

    private MainThread thread;

    public ClimateGameIntroScene(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "MYLOG-Surface is being destroyed");
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch(InterruptedException e) {

            }
        }
        Log.d(TAG, "MYLOG-Thread was shutdown");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "MYLOG-Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint r = new Paint();
        r.setColor(Color.WHITE);
        canvas.drawRect(10, 10, 200, 200, r);
    }
}
