package com.css.msgserver.ws.client;

import java.util.HashMap;
import java.util.Map;

public class WSClientFactory {
	private static Map<String,IServiceProxy> cache=new HashMap<String,IServiceProxy>();
	public static <T> T  get(Class<T> clz) throws Exception{
		WSModel<T> sd=new WSModel<T>(clz);
		if(!sd.isModel()){
			throw new RuntimeException(clz.getName()+"未配置【@WebServiceClient】注解!");
		}
		IServiceProxy proxy=getCache(sd.getWsdlLocation());
		if(proxy==null){
			proxy=WSServicesAdapter.getProxy(sd);
			setCache(sd.getWsdlLocation(),proxy);
		}
		return proxy.getService(clz);
	}
	private static IServiceProxy getCache(String url) {
		return cache.get(url);
	}
	private static void setCache(String url,IServiceProxy c) {
		cache.put(url,c);
	}
	public static void main(String[] args) throws Exception{
		ITestService ip =WSClientFactory.get(ITestService.class);
		System.out.println(ip.receive("1111111111111111111>>>"));
	}
}
