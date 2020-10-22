package com.firdadev.runsheetv2.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse{

	@SerializedName("status")
	private boolean status;

	public boolean isStatus(){
		return status;
	}
}