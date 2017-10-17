package com.css.msgserver.bank.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("DATAPACKET")
public class ListXSDataPacket extends AbstracXSDataPacket{
	@XStreamAlias("DATASET")
	private ListDataSet DATASET=new ListDataSet();

	public ListDataSet getDATASET() {
		return DATASET;
	}

	public void setDATASET(ListDataSet dATASET) {
		DATASET = dATASET;
	}
}
