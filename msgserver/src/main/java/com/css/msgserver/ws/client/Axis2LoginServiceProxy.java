package com.css.msgserver.ws.client;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class Axis2LoginServiceProxy extends AbstractServiceProxy{

	public Axis2LoginServiceProxy(WSModel<?> ws) {
		super(ws);
	}
	@Override
	protected void setConf(RPCServiceClient serviceClient, Options options, WSModel<?> wsModel) {
		HeaderElement header=new HeaderElement("www.csscis.com","csscis");
		serviceClient.addHeader(header.getHeader(wsModel.getRole(), wsModel.getUsername(), wsModel.getPassword()));
	}

}
