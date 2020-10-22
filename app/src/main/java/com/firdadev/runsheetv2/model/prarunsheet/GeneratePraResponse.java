package com.firdadev.runsheetv2.model.prarunsheet;

import com.google.gson.annotations.SerializedName;

public class GeneratePraResponse{

	@SerializedName("no_praposting")
	private String noPraposting;

	@SerializedName("status")
	private boolean status;

	public String getNoPraposting(){
		return noPraposting;
	}

	public boolean isStatus(){
		return status;
	}
}