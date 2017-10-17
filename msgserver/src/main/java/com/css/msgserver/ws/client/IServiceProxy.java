package com.css.msgserver.ws.client;

public interface IServiceProxy {
	public <T> T getService(Class<T> clz) throws Exception;
}
