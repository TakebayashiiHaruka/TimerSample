package jp.ac.shohoku.s18bxxx.timersample;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Pict {
    private int mX;
    private int mY;
    private int mW;
    private int mH;
    private Bitmap mBmp;
    private int mDx;
    private int mDy;

    public Pict(int x, int y, Bitmap bmp){
        mX = x;
        mY =y;
        mBmp = bmp;
        mDx = 0;
        mDy = 0;
    }

    public void draw(Canvas canvas){
        Paint p = new Paint();
        canvas.drawBitmap(mBmp, mX, mY, p);
    }
    public void draw(Canvas canvas, Paint paint){
        canvas.drawBitmap(mBmp, mX, mY, paint);
    }

    public int getmX() {
        return mX;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }

    public int getmY() {
        return mY;
    }

    public void setmY(int mY) {
        this.mY = mY;
    }

    public int getmW() {
        return mW;
    }

    public void setmW(int mW) {
        this.mW = mW;
    }

    public int getmH() {
        return mH;
    }

    public void setmH(int mH) {
        this.mH = mH;
    }

    public Bitmap getmBmp() {
        return mBmp;
    }

    public void setmBmp(Bitmap mBmp) {
        this.mBmp = mBmp;
    }

    public int getmDx() {
        return mDx;
    }

    public void setmDx(int mDx) {
        this.mDx = mDx;
    }

    public int getmDy() {
        return mDy;
    }

    public void setmDy(int mDy) {
        this.mDy = mDy;
    }
}
