package com.stlight.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2/10/2018.
 */

public class DayVo {
    private String day;
    private boolean checked;
    ArrayList<String> daylist= new ArrayList<String>();


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getDay() {

        return day;
    }

    public void setDay(String day) {
        this.day = day;

    }

}
