package com.css.msgserver.ws.client;

import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

public class SimpleServiceProxy extends AbstractServiceProxy{

	public SimpleServiceProxy(WSModel<?> ws) {
		super(ws);
	}
	@Override
	protected void setConf(RPCServiceClient serviceClient, Options options, WSModel<?> wsModel) {
		System.out.println("simple axis client");
	}

}
