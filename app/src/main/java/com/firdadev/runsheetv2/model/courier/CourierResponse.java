package com.firdadev.runsheetv2.model.courier;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CourierResponse{

	@SerializedName("detail")
	private List<DetailItem> detail = null;

	@SerializedName("status")
	private boolean status;

	public List<DetailItem> getDetail(){
		return detail;
	}

	public boolean isStatus(){
		return status;
	}
}