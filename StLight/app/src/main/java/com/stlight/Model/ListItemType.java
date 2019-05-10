package com.stlight.Model;

public abstract class ListItemType {
    public static final int TYPE_DATE = 0;
    public static final int TYPE_GENERAL = 1;

    abstract public int getType();
}