package core;

import com.stlight.Model.DataObject;
import com.stlight.Model.DateItem;
import com.stlight.Model.GroupOfData;
import com.stlight.Model.ListItemType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ConsolidatedList {
    public static  List<ListItemType> consolidatedList(LinkedHashMap<String, List<DataObject>> groupedHashMap){
        int i=0;
        List<ListItemType> consolidatedList = new ArrayList<>();
        for (String date : groupedHashMap.keySet()) {

            DateItem dateItem = new DateItem();
            dateItem.setDate(date);
            consolidatedList.add(dateItem);

            for (DataObject dataObject : groupedHashMap.get(date)) {
                if(i++ == groupedHashMap.get(date).size() - 1) {
                    GroupOfData generalItem = new GroupOfData();
                    generalItem.setDataObject(dataObject);
                    consolidatedList.add(generalItem);
                }
            }

        }
        return consolidatedList;
    }
}