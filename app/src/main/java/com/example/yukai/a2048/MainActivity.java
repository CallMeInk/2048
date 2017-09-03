package com.example.yukai.a2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private final static int N = 4;

    private final static int LEFT = 0;

    private final static int RIGHT = 1;

    private final static int UP = 2;

    private final static int DOWN = 3;

    private ArrayList<ItemModel> models = new ArrayList<>();

    private int[] mNumbers = new int[16];

    private GridLayout mGridLayout;

    private float startX;

    private float startY;

    private float endX;

    private float endY;

    private View.OnTouchListener mGridLayoutOntouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    endX = event.getX();
                    endY = event.getY();
                    doAnimation(getSlideDirection(endX - startX, endY - startY));
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData(){
        initNumbers();
        mGridLayout = (GridLayout)findViewById(R.id.grid_layout);
        mGridLayout.setOnTouchListener(mGridLayoutOntouchListener);
        refreshGridView();
    }

    private void initNumbers(){
        for (int i = 0;i < N * N;i++){
            mNumbers[i] = 0;
        }
        for (int i = 0;i < N;i++){
            addRandomNumber();
        }
    }

    private void refreshGridView(){
        for (int i = 0;i < N * N;i++){
            TextView textView = (TextView)mGridLayout.getChildAt(i);
            textView.setText(String.valueOf(mNumbers[i]));
            textView.setBackgroundColor(ItemColor.getInstance().getColor(mNumbers[i]));
        }
    }

    private int getSlideDirection(float x, float y){
        return Math.abs(x) > Math.abs(y)? ((x > 0)? RIGHT : LEFT) : ((y > 0) ? DOWN : UP);
    }

    private void doAnimation(int direction){
        switch (direction){
            case LEFT:
                for(int i = 0;i < N * N;i += N){
                    doLeftAnimation(i);
                }
                break;
            case RIGHT:
                for(int i = 3;i < N * N;i += N){
                    doRightAnimation(i);
                }
                break;
            case UP:
                for(int i = 0;i < N;i++){
                    doUpAnimation(i);
                }
                break;
            case DOWN:
                for(int i = N * (N - 1);i < N * N;i++){
                    doDownAnimation(i);
                }
                break;
            default:
                break;
        }
        refreshGridView();
    }

    private void addRandomNumber(){
        boolean hasBlank = false;
        for (int i = 0;i < N * N;i++){
            if (mNumbers[i] == 0){
                hasBlank = true;
                break;
            }
        }
        if (!hasBlank) return;
        Random random = new Random();
        int number;
        do{
            number = random.nextInt(N * N);
        }
        while (mNumbers[number] != 0);
        mNumbers[number] = 2;
    }

    private void doLeftAnimation(int index){
        slideToLeft(index);
        if (mNumbers[index] == mNumbers[index + 1] && mNumbers[index] != 0){
            mNumbers[index] = mNumbers[index] * 2;
            mNumbers[index + 1] = 0;
            if (mNumbers[index + 2] == mNumbers[index + 3] && mNumbers[index + 2] != 0){
                mNumbers[index + 2] = mNumbers[index + 2] * 2;
                mNumbers[index + 3] = 0;
            }
        }else if (mNumbers[index + 1] == mNumbers[index + 2] && mNumbers[index + 1] != 0){
            mNumbers[index + 1] = mNumbers[index + 1] * 2;
            mNumbers[index + 2] = 0;
        }
        slideToLeft(index);
    }

    private void slideToLeft(int index){
        for (int i = 0;i < N - 1;i++){
            if (mNumbers[index + i] == 0){
                for (int j = i;j < N - 1;j++){
                    mNumbers[index + j] = mNumbers[index + j + 1];
                }
                mNumbers[index + N - 1] = 0;
            }
        }
    }

    private void doRightAnimation(int index){
        slideToRight(index);
        if (mNumbers[index] == mNumbers[index - 1] && mNumbers[index] != 0){
            mNumbers[index] = mNumbers[index] * 2;
            mNumbers[index - 1] = 0;
            if (mNumbers[index - 2] == mNumbers[index - 3] && mNumbers[index - 2] != 0){
                mNumbers[index - 2] = mNumbers[index - 2] * 2;
                mNumbers[index - 3] = 0;
            }
        }else if (mNumbers[index - 1] == mNumbers[index - 2] && mNumbers[index - 1] != 0){
            mNumbers[index - 1] = mNumbers[index - 1] * 2;
            mNumbers[index - 2] = 0;
        }
        slideToRight(index);
    }

    private void slideToRight(int index){
        for (int i = 0;i < N - 1;i++){
            if (mNumbers[index - i] == 0){
                for (int j = i;j < N - 1;j++){
                    mNumbers[index - j] = mNumbers[index - j - 1];
                }
                mNumbers[index - N + 1] = 0;
            }
        }
    }

    private void doUpAnimation(int index){
        slideToUp(index);
        if (mNumbers[index] == mNumbers[index + N] && mNumbers[index] != 0){
            mNumbers[index] = mNumbers[index] * 2;
            mNumbers[index + N] = 0;
            if (mNumbers[index + 2 * N] == mNumbers[index + 3 * N] && mNumbers[index + 2 * N] != 0){
                mNumbers[index + 2 * N] = mNumbers[index + 2 * N] * 2;
                mNumbers[index + 3 * N] = 0;
            }
        }else if (mNumbers[index + N] == mNumbers[index + 2 * N] && mNumbers[index + N] != 0){
            mNumbers[index + N] = mNumbers[index + N] * 2;
            mNumbers[index + N * 2] = 0;
        }
        slideToUp(index);
    }

    private void slideToUp(int index){
        for (int i = 0;i < N * N;i += N){
            if (mNumbers[index + i] == 0){
                for (int j = i;j < N * N;j++){
                    mNumbers[index + j] = mNumbers[index + j + N];
                }
                mNumbers[index + N * (N - 1)] = 0;
            }
        }
    }

    private void doDownAnimation(int index){
        slideToDown(index);
        if (mNumbers[index] == mNumbers[index - N] && mNumbers[index] != 0){
            mNumbers[index] = mNumbers[index] * 2;
            mNumbers[index - N] = 0;
            if (mNumbers[index - 2 * N] == mNumbers[index - 3 * N] && mNumbers[index - 2 * N] != 0){
                mNumbers[index - 2 * N] = mNumbers[index - 2 * N] * 2;
                mNumbers[index - 3 * N] = 0;
            }
        }else if (mNumbers[index - N] == mNumbers[index - 2 * N] && mNumbers[index - N] != 0){
            mNumbers[index - N] = mNumbers[index - N] * 2;
            mNumbers[index - N * 2] = 0;
        }
        slideToUp(index);
    }

    private void slideToDown(int index){
        for (int i = 0;i < N * N;i += N){
            if (mNumbers[index - i] == 0){
                for (int j = i;j < N * N;j++){
                    mNumbers[index - j] = mNumbers[index - j - N];
                }
                mNumbers[index - N * (N - 1)] = 0;
            }
        }
    }
}
