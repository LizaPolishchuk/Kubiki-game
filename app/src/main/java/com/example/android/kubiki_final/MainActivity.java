package com.example.android.kubiki_final;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int w, h;
    DisplayMetrics displayMetrics;
    long timeBefore = 0;
    long timeAfter = 0;
    long timeLost = 0;
    SimpleDateFormat sdf;
    boolean flag = false;
    String textTime = "00:00";
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        w = displayMetrics.widthPixels;
        h = displayMetrics.heightPixels;
        sdf = new SimpleDateFormat("ss:SS");
        setContentView(new DrawView(this));
        getSupportActionBar().hide();
    }

    class DrawView extends View {

        Random random;
        Paint paint;
        RectF rectf;
        int side = 300;
        int x = (w - (side + 4)) / 2;
        int y = (h - (side + 60)) / 2;
        String text = "0";


        public DrawView(Context context) {
            super(context);
            paint = new Paint();
            paint.setStrokeWidth(3);
            random = new Random();
            rectf = new RectF();
            timeBefore = System.currentTimeMillis();
            color = Color.parseColor("#F50057");
        }

        @Override
        protected void onDraw(Canvas canvas) {
            paint.setTextSize(100);
            paint.setColor(Color.parseColor("#F50057"));
            canvas.drawText(text, 30, 100, paint);
            canvas.drawText(textTime, 460, 100, paint);
            rectf.set(x, y, x + side, y + side);
            paint.setColor(color);
            canvas.drawRect(rectf, paint);
            if (flag == true) {
                myInvalidate();
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float evX = event.getX();
            float evY = event.getY();
            final AlertDialog.Builder builder;
            AlertDialog dialog;
            int count = Integer.valueOf(text);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // если касание было начато в пределах квадрата
                if (evX >= x && evX <= x + side && evY >= y && evY <= y + side) {
                    if (!flag) {
                        flag = true;
                        invalidate();
                    }
                    x = random.nextInt(w - (side + 4));
                    y = random.nextInt(h - (side + 60));
                    rectf.offsetTo(x, y);
                    if (side <= 80) side = 80;
                    side -= 10;
                    count++;
                    text = String.valueOf(count);
                    color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    invalidate();
                } else {
                    flag = false;
                    builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Ты проиграл!");
                    builder.setMessage("Счет: " + count + "\n" + "Время: " + textTime);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Заново", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            side = 300;
                            x = (w - (side + 4)) / 2;
                            y = (h - (side + 60)) / 2;
                            text = "0";
                            textTime = "00:00";
                            timeBefore = System.currentTimeMillis();
                            invalidate();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            }
            return true;
        }

        void myInvalidate() {
            timeAfter = System.currentTimeMillis();
            timeLost = timeAfter - timeBefore;
//            if(timeLost>100000){
//                flag = false;
//                timeLost = 0;
//                timeBefore = System.currentTimeMillis();
//            }
            textTime = sdf.format(timeLost);
            invalidate();
        }
    }
}
