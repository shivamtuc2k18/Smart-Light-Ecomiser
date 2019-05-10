package com.stlight.Model;

public class GroupOfData extends ListItemType{

    private DataObject dataObject;

    public DataObject getDataObject() {
        return dataObject;
    }

    public void setDataObject(DataObject dataObject) {

        this.dataObject = dataObject;
    }


    @Override
    public int getType() {
        return TYPE_GENERAL;
    }
}