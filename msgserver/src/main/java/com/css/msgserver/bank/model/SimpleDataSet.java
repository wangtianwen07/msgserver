package com.css.msgserver.bank.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DATASET")
public class SimpleDataSet implements IDataSet{
	@XStreamAlias("DATA")
	private String DATA="";
	
	@XStreamAlias("YWSJJ")
	private String YWSJJ="";
	public String getDATA() {
		return DATA;
	}

	public void setDATA(String dATA) {
		DATA = dATA;
	}
	
	public String getYWSJJ() {
		return YWSJJ;
	}

	public void setYWSJJ(String yWSJJ) {
		YWSJJ = yWSJJ;
	}
	
}
