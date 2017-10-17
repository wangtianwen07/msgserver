package com.css.msgserver.bank.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
@XStreamAlias("DATAPACKET")
public class BaseXSDataPacket extends AbstracXSDataPacket{
	//
	@XStreamOmitField
	@XStreamAlias("DATASET")
	private String DATASET="";
}
