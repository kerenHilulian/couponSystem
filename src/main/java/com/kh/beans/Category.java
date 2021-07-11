package com.kh.beans;

public enum Category {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    PC,
    SPA,
    CLOTHING;

    public final int value = 1 + ordinal();

    public static Category getCategory(int index){
        return Category.values()[index-1];
    }
}
