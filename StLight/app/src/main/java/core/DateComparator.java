package core;

import com.stlight.Model.DataObject;

import java.util.Comparator;
import java.util.Date;

public class DateComparator implements Comparator<DataObject> {



    @Override
    public int compare(DataObject d1, DataObject d2) {
        Date date1 = new Date(Long.parseLong(d1.getDateInMills()));
        Date date2 = new Date(Long.parseLong(d2.getDateInMills()));
        return date2.compareTo(date1);
    }
}