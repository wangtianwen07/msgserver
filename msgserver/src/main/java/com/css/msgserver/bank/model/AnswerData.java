package com.css.msgserver.bank.model;

import com.css.msgserver.jpa.pojo.Pack;

public class AnswerData {
	@Pack(order=0)
	private String fhbz;
	@Pack(order=1)
	private String fhsbxx;
	public String getFhbz() {
		return fhbz;
	}
	public void setFhbz(String fhbz) {
		this.fhbz = fhbz;
	}
	public String getFhsbxx() {
		return fhsbxx;
	}
	public void setFhsbxx(String fhsbxx) {
		this.fhsbxx = fhsbxx;
	}
}
