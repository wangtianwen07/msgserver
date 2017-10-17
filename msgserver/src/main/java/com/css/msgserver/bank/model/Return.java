package com.css.msgserver.bank.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("RETURN")
public class Return {
	@XStreamAlias("IRETURN")
	private String IRETURN=IReturn.success;
	
	@XStreamAlias("MSGRETURN")
	private String MSGRETURN="";

	public String getIRETURN() {
		return IRETURN;
	}

	public void setIRETURN(String iRETURN) {
		IRETURN = iRETURN;
	}

	public String getMSGRETURN() {
		return MSGRETURN;
	}

	public void setMSGRETURN(String mSGRETURN) {
		MSGRETURN = mSGRETURN;
	}
	
	
}
