package com.example.yukai.a2048;

import android.graphics.Color;

/**
 * Created by yukai on 2017/9/3.
 */

public class ItemColor {

    private ItemColor(){}

    private static ItemColor sItemColor;

    public static ItemColor getInstance(){
        if (sItemColor == null){
            sItemColor = new ItemColor();
        }
        return sItemColor;
    }

    public int getColor(int number){
        switch (number){
            case 0:
                return Color.parseColor("#ffffff");
            case 2:
                return Color.parseColor("#ff0000");
            default:
                return Color.parseColor("#00ff00");
        }
    }
}
