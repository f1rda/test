package com.firdadev.runsheetv2.adapter;


import android.content.Context;

import com.firdadev.runsheetv2.model.courier.DetailItem;

import java.util.ArrayList;
import java.util.List;

public class CourierAdapter {

    private List<DetailItem> detailItems = new ArrayList<>();
    private Context mContext;

    public CourierAdapter() {
    }

    public CourierAdapter(List<DetailItem> detailItems,Context context) {
        this.detailItems = detailItems;
        this.mContext=context;
    }


}
