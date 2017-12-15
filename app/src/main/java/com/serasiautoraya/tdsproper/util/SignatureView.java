package com.serasiautoraya.tdsproper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.serasiautoraya.tdsproper.util.HelperBridge;

import java.io.FileOutputStream;

/**
 * Created by Randi Dwi Nandra on 21/11/2016.
 */
public class SignatureView extends View {
    private static final float STROKE_WIDTH = 5f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private Paint paint = new Paint();
    private Path path = new Path();

    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();

    private Bitmap bitmap;
    private LinearLayout mContainer;
    private Button btnGetSign;

    public SignatureView(Context context, AttributeSet attrs, LinearLayout container, Button getSignButton) {
        super(context, attrs);
        this.mContainer = container;
        this.btnGetSign = getSignButton;

        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    public void save(View v, String StoredPath) {
        Log.v("SIGN_TAG", "Width: " + v.getWidth());
        Log.v("SIGN_TAG", "Height: " + v.getHeight());
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(mContainer.getWidth(), mContainer.getHeight(), Bitmap.Config.RGB_565);
        }
        //Spesial akses ke HelperBridge
        HelperBridge.sTtdBitmap = bitmap;
        com.serasiautoraya.tdsproper.Helper.HelperBridge.sBitmapSignature = bitmap;
        //---
        Canvas canvas = new Canvas(bitmap);
        try {
            // Output the file
            FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
            v.draw(canvas);
            // Convert the output file to Image such as .jpeg
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();
        } catch (Exception e) {
            Log.v("SIGN_TAG", e.toString());
        }
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        btnGetSign.setEnabled(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;

            case MotionEvent.ACTION_MOVE:

            case MotionEvent.ACTION_UP:
                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;
            default:
                Log.v("SIGN_TAG", "Ignored touch event: " + event.toString());
                return false;
        }

        invalidate(
                (int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }
}
