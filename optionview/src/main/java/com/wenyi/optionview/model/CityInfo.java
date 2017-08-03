package com.wenyi.optionview.model;


/**
 * Created by Administrator on 2016/10/26.
 */

public class CityInfo implements IPickerViewData {
    public String name;
    public String id;

    public CityInfo(String name,String id){
        this.name=name;
        this.id=id;
    }
    @Override
    public String getPickerViewText() {
        return name;
    }
}
