package com.css.msgserver.ws.client;

public class WSServicesAdapter {
	public static IServiceProxy getProxy(WSModel<?> ws){
		
		if(ws.getType()==null || ws.getType().equals(WSModel.TYPE_SIMPLE)){
			return new SimpleServiceProxy(ws);
		}else if(ws.getType().equals(WSModel.TYPE_AXIS2LOGIN)){
			return new Axis2LoginServiceProxy(ws);
		}else{
			throw new RuntimeException("appconfig.properties未配置【type】正确！");
		}
	}
}
