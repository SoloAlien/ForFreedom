package com.kpw.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.sax.RootElement;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.nio.channels.Channels;
import java.util.ArrayList;

public class Mygrid extends View {
    private Paint paint;
    private int colum;
    private int row;
    private int tileSize;
    private int width, height;
    private Context context;
    private Bitmap bitmap;
    private ArrayList<Integer> imgs = new ArrayList<>();


    public Mygrid(Context context, int colum, int row) {
        super(context);
        this.colum = colum;
        this.row = row;
    }

    private void initBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.d99, null);
        bitmap = bitmapDrawable.getBitmap();
//        bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(1);
    }

    public Mygrid(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs);
    }


    public Mygrid(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initParams(Context context, AttributeSet attrs) {
        initBitmap();
        initPaint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Mygrid);
        if (typedArray != null) {
            colum = typedArray.getInt(R.styleable.Mygrid_column, 0);
            row = typedArray.getInteger(R.styleable.Mygrid_row, 0);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        tileSize = width / colum;
        height = tileSize * row;
        setMeasuredDimension(width, height);
    }

    public void setImages(ArrayList<Integer> images) {
        this.imgs = images;
    }

    public void setBitmap(int id) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(id, null);
        bitmap = bitmapDrawable.getBitmap();
        postInvalidate();
    }

    public void setbitmapList(ArrayList<Integer> imgs) {
        this.imgs = imgs;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画竖线 纵坐标不变
        for (int i = 0; i <= colum; i++) {
            canvas.drawLine(tileSize * i, 0, tileSize * i, height, paint);
            Log.e("TAG", "onDraw: x->:" + tileSize * i);
            if (i == 0) {
                continue;
            }
        }
        //画横线 横坐标不变，纵坐标会发生变化
        for (int j = 0; j <= row; j++) {
            canvas.drawLine(0, tileSize * j, width, tileSize * j, paint);
            if (j == 0) {
                continue;
            }
        }
        int total = 0;
        if (imgs.size() > row * colum) {
            total = row * colum;
        } else total = imgs.size();

        for (int m = 0; m < total; m++) {
            int line = total / colum;//满行的行数
            int lastRowIndex = total % colum;//最后一行的条目数

            if (lastRowIndex > 0) {
                for (int a = 0; a < line + 1; a++) {
                    if (a <line) {
                        for (int b = 0; b < colum; b++) {
                            Rect dst = new Rect(tileSize * b, tileSize * a, tileSize * (b + 1), tileSize * (a + 1));
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(a * b), null);
                            bitmap = bitmapDrawable.getBitmap();
                            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            canvas.drawBitmap(bitmap, rect, dst, paint);
                        }
                    } else {
                        for (int b = 0; b < lastRowIndex; b++) {
                            Rect dst = new Rect(tileSize * b, tileSize * line, tileSize * (b + 1), tileSize * (line + 1));
                            BitmapDrawable bitmapDrawable = null;
                            if (line == 0) {
                                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(b), null);
                            } else
                                bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(colum * (line - 1) + b), null);

                            bitmap = bitmapDrawable.getBitmap();
                            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            canvas.drawBitmap(bitmap, rect, dst, paint);
                        }
                    }
                }
            }else {
                for (int a = 0; a < line + 1; a++) {
                    for (int b = 0; b < colum; b++) {
                        Rect dst = new Rect(tileSize * b, tileSize * a, tileSize * (b + 1), tileSize * (a + 1));
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(a * b), null);
                        bitmap = bitmapDrawable.getBitmap();
                        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                        canvas.drawBitmap(bitmap, rect, dst, paint);
                    }
                }
            }
        }


//
//        int line = imgs.size() / colum;//满行的行数
//        int lastRowIndex = imgs.size() % colum;//最后一行的条目数
//        if (line == 0 && lastRowIndex > 0) {//如果只有一行
//            for (int k = 0; k < lastRowIndex; k++) {
//                Rect dst = new Rect(tileSize * k, 0, tileSize * (k + 1), tileSize * (line + 1));
//                BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(k), null);
//                bitmap = bitmapDrawable.getBitmap();
//                Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                canvas.drawBitmap(bitmap, rect, dst, paint);
//            }
//        }
//        if (line > 0) {
//            if (lastRowIndex == 0) {
//                for (int a = 0; a < line; a++) {
//                    for (int b = 0; b < colum; b++) {
//                        Rect dst = new Rect(tileSize * b, tileSize * a, tileSize * (b + 1), tileSize * (a + 1));
//                        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(a * b), null);
//                        bitmap = bitmapDrawable.getBitmap();
//                        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                        canvas.drawBitmap(bitmap, rect, dst, paint);
//                    }
//                }
//            }
//            if (lastRowIndex > 0) {
//                for (int a = 0; a < line + 1; a++) {
//                    if (a < line) {
//                        for (int b = 0; b < colum; b++) {
//                            Rect dst = new Rect(tileSize * b, tileSize * a, tileSize * (b + 1), tileSize * (a + 1));
//                            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(a * b), null);
//                            bitmap = bitmapDrawable.getBitmap();
//                            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                            canvas.drawBitmap(bitmap, rect, dst, paint);
//                        }
//                    } else {
//                        for (int b = 0; b < lastRowIndex; b++) {
//                            Rect dst = new Rect(tileSize * b, tileSize * line, tileSize * (b + 1), tileSize * (line + 1));
//                            BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(imgs.get(colum * (line - 1) + b), null);
//                            bitmap = bitmapDrawable.getBitmap();
//                            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//                            canvas.drawBitmap(bitmap, rect, dst, paint);
//                        }
//                    }
//                }
//            }
//        }
    }
}

