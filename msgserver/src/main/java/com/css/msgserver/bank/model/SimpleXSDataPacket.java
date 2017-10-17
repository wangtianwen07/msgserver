package com.css.msgserver.bank.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
@XStreamAlias("DATAPACKET")
public class SimpleXSDataPacket extends AbstracXSDataPacket{
	
	@XStreamAlias("DATASET")
	private SimpleDataSet DATASET=new SimpleDataSet();

	public SimpleDataSet getDATASET() {
		return DATASET;
	}

	public void setDATASET(SimpleDataSet dATASET) {
		DATASET = dATASET;
	}

}
