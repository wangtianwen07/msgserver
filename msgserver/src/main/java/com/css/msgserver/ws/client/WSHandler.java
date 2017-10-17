package com.css.msgserver.ws.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.jws.WebMethod;
import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.commons.lang.StringUtils;

public class WSHandler implements InvocationHandler {
	private RPCServiceClient  serviceClient;
	private WSModel<?> wsModel;
	public WSHandler(RPCServiceClient  serviceClient,WSModel<?> m){
		this.serviceClient=serviceClient;
		this.wsModel=m;
	}
	public Object invoke(Object proxy, Method method, Object[] args,String namespace) throws AxisFault {
		Object ret=null;
		String qname=method.getName();
		if(method.isAnnotationPresent(WebMethod.class)){
			WebMethod wm=method.getAnnotation(WebMethod.class);
			if(StringUtils.isNotEmpty(wm.action())){
				qname=wm.action();
			}
		}
		serviceClient.getOptions().setAction(qname);
		QName opQName = new QName(namespace, qname);
		Class<?>[] opReturnType=new Class<?>[]{method.getReturnType()};
		ret=serviceClient.invokeBlocking(opQName, args, opReturnType)[0];
		return ret;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return invoke(proxy, method,args,wsModel.getTargetNamespace());
	}

}
