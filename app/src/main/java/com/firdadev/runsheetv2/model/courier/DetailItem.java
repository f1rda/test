package com.firdadev.runsheetv2.model.courier;

import com.google.gson.annotations.SerializedName;

public class DetailItem{

	@SerializedName("courier_name")
	private String courierName;

	@SerializedName("courier_id")
	private String courierId;

	public String getCourierName(){
		return courierName;
	}

	public String getCourierId(){
		return courierId;
	}
}