package com.css.msgserver.bank.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("WHERE")
public class Where {
	@XStreamAlias("YWLX")
	private String YWLX="";
	
	@XStreamAlias("JYLSH")
	private String JYLSH="";

	public String getYWLX() {
		return YWLX;
	}

	public void setYWLX(String yWLX) {
		YWLX = yWLX;
	}

	public String getJYLSH() {
		return JYLSH;
	}

	public void setJYLSH(String jYLSH) {
		JYLSH = jYLSH;
	}
	
}
