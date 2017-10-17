package com.css.msgserver.bank.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("DATASET")
public class ListDataSet {
	@XStreamAlias("DATA")
	private String DATA="";
	
	@XStreamImplicit(itemFieldName="YWSJJ")  
	private List<String> YWSJJ=new ArrayList<String>();

	public String getDATA() {
		return DATA;
	}

	public void setDATA(String dATA) {
		DATA = dATA;
	}

	public List<String> getYWSJJ() {
		return YWSJJ;
	}

	public void setYWSJJ(List<String> yWSJJ) {
		YWSJJ = yWSJJ;
	}
	
	public void addYwsjj(String e){
		YWSJJ.add(e);
	}
	
}
