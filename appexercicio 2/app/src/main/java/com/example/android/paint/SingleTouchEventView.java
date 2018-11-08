package com.example.android.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SingleTouchEventView extends View implements SensorEventListener {

    private Paint paint = new Paint();
    private Path path = new Path();
    private Canvas canvass ;

    private int[] colors = {Color.BLACK, Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.CYAN};
    private int colorN = 1;
    private int backgroundN = 1;

    private GestureDetectorCompat mDetector;
    private Context context;

    private SensorManager sensorManager;
    private Sensor sensorPox;
    private Sensor sensorGrav;

    private boolean color = false;
    private long lastUpdate;

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private static final float SHAKE_THRESHOLD = 3.25f;
    private static final float SHAKE_THRESHOLD1 = 2.25f;// m/S**2
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    private SensorManager mSensorMgr;



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long curTime = System.currentTimeMillis();
                if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    double acceleration = Math.sqrt(Math.pow(x, 2) +
                            Math.pow(y, 2) +
                            Math.pow(z, 2)) - SensorManager.GRAVITY_EARTH;
                    Log.d("APP_NAME", "Acceleration is " + acceleration + "m/s^2");

                    if (acceleration > SHAKE_THRESHOLD) {
                        mLastShakeTime = curTime;
                        path.reset();
                        invalidate();
                        Toast.makeText(getContext(),"SHAKE MAIOR",Toast.LENGTH_LONG).show();

                        //Log.d("APP_NAME", "Shake, Rattle, and Roll");
                    }

                    if (acceleration < SHAKE_THRESHOLD && acceleration > SHAKE_THRESHOLD1) {
                        mLastShakeTime = curTime;

                        path.rewind();
                        Toast.makeText(getContext(),"SHAKE MENOR",Toast.LENGTH_LONG).show();


                        //Log.d("APP_NAME", "Shake, Rattle, and Roll");

                    }

                }
            }




        }
        else if(event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if(event.values[0] == 0) {
                setBackgroundColor(Color.BLACK);
                //Toast.makeText(getContext(), "working", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdate < 200) {
                return;
            }
            lastUpdate = actualTime;
            Toast.makeText(context, "Device was shuffed", Toast.LENGTH_SHORT).show();

            path.reset();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            mScaleFactor *= detector.getScaleFactor();


            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 50.0f));

            paint.setStrokeWidth(mScaleFactor);

            invalidate();
            return true;
        }
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            path.moveTo(eventX, eventY);
            return true;
        }

        @Override
        public void onLongPress(MotionEvent event) {
            setBackgroundColor(colors[backgroundN]);
            backgroundN++;
            if(backgroundN >= colors.length)
                backgroundN = 0;

        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {

            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            paint.setColor(colors[colorN]);
            colorN = colorN + 1;
            if(colorN >= colors.length)
                colorN = 0;
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {

            return true;
        }


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            return true;
        }
    }



    /*
     *
     * *
     * *
     * *
     * *Parte realmente da classe
     * *
     * *
     */

private ArrayList<Paint> array = new ArrayList<Paint>();
    public SingleTouchEventView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeJoin(Paint.Join.ROUND);

        paint.setTextSize(86);

        canvass = new Canvas();
        
        this.context = context;
        mDetector = new GestureDetectorCompat(context, new MyGestureListener());

        sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        sensorPox = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorGrav = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensorGrav, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorPox, SensorManager.SENSOR_DELAY_NORMAL);

        lastUpdate = System.currentTimeMillis();

        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //canvas.scale(mScaleFactor, mScaleFactor);
        canvas.drawPath(path, paint);



    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mScaleDetector.onTouchEvent(event);

        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return this.mDetector.onTouchEvent(event);
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            default:
                return this.mDetector.onTouchEvent(event);
        }

        // Schedules a repaint.
        invalidate();
        return super.onTouchEvent(event);
    }
}