package com.firdadev.runsheetv2.model.prarunsheet;

import com.firdadev.runsheetv2.model.courier.DetailItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PraRunsheetResponse {

    @SerializedName("msg")
    private String msg;

    @SerializedName("status")
    private boolean status;
}
