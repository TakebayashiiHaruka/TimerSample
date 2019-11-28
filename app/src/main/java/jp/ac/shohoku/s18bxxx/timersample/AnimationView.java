package jp.ac.shohoku.s18bxxx.timersample;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AnimationView extends SurfaceView implements SurfaceHolder.Callback , Runnable{
    private SurfaceHolder mHolder;
    private Pict[] mPict;
    private int mX, mY;
    private boolean mIsTap;
    private int mWidth, mHeight;

    public AnimationView(Context context) {
        super(context);
        init();
    }
    public AnimationView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }
    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        requestFocus();

        mPict = new Pict[25];
        for(int i=0; i<mPict.length; i++){
            Resources rs = this.getContext().getResources();
            Bitmap bmp = BitmapFactory.decodeResource(rs, R.mipmap.ic_launcher);
            mPict[i] = new Pict(-999, -999, bmp);
        }
        mIsTap = true;
    }

    private void start(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(this, 30, 30, TimeUnit.MILLISECONDS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        draw();
        start();
        mWidth = this.getWidth();
        mHeight = this.getHeight();
    }


    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public boolean onTouchEvent(MotionEvent event){
        int action = event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                mIsTap = true;
                mX = (int)event.getX();
                mY = (int)event.getY();
                for(int i=0; i< mPict.length; i++){
                    mPict[i].setmDx(0);
                    mPict[i].setmDy(0);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mX = (int)event.getX();
                mY = (int)event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mX = (int)event.getX();
                mY = (int)event.getY();
                for(int i=1; i< mPict.length; i++){
                    mPict[i].setmDx(mPict[i-1].getmX()-mPict[i].getmX());
                    mPict[i].setmDy(mPict[i-1].getmY()-mPict[i].getmY());
                }
                mPict[0].setmDx(mX-mPict[0].getmX());
                mPict[0].setmDy(mY-mPict[0].getmY());
                mIsTap = false;
                break;

        }
        return true;
    }

    public void adddPoint(int x, int y) {
        for (int i = mPict.length - 2; i >= 0; i--) {
            mPict[i + 1].setmX(mPict[i].getmX());
            mPict[i + 1].setmY(mPict[i].getmY());
        }
        mPict[0].setmX(mX);
        mPict[0].setmY(mY);
    }

     private void move(){
        for(int i=0; i<mPict.length; i++){
            int nextX = mPict[i].getmX()+mPict[i].getmDx()/3;
            if(nextX > mWidth-mPict[i].getmW()){
                mPict[i].setmX(mWidth - mPict[i].getmW());
                mPict[i].setmDx(-mPict[i].getmDx());
            } else if(nextX < 0) {
              mPict[i].setmX(0);
              mPict[i].setmDx(-mPict[i].getmDx());
            } else {
                mPict[i].setmX(nextX);
            }

            int nextY = mPict[i].getmY()+mPict[i].getmDy()/3;
            if(nextY > mHeight-mPict[i].getmH()){
                mPict[i].setmY(mHeight - mPict[i].getmH());
                mPict[i].setmDy(-mPict[i].getmDy());
            } else if(nextY < 0) {
                mPict[i].setmY(0);
                mPict[i].setmDy(-mPict[i].getmDy());
            } else {
                mPict[i].setmY(nextY);
            }
        }

    }

    private void draw() {
        Canvas canvas = mHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        Paint p = new Paint();
        for(int i=0; i< mPict.length; i++){
            mPict[i].draw(canvas, p);
        }
        mHolder.unlockCanvasAndPost(canvas);
    }


    @Override
    public void run() {
        if(mIsTap) {
            adddPoint(mX, mY);
        } else {
            move();
        }
        draw();
    }
}
