package com.css.msgserver;

import java.net.MalformedURLException;

import org.junit.Test;

import com.caucho.hessian.client.HessianProxyFactory;
import com.css.msgserver.csscis.hession.service.ISmsService;
import com.css.msgserver.csscis.hession.token.service.Token;
public class HessianTest {
	@Test
	public void testWs(){
		System.out.println("sd");
		//Axis2RPCFactory.test();
	}
	@Test
	public void testHessian() throws MalformedURLException{
		for(int i=0;i<1;i++){
			testSend();
		}
	}
	@Test
	public void testSend() throws MalformedURLException{
//		HessianProxyFactory factory = new HessianProxyFactory();
//		final String url="http://127.0.0.1:9988/sms/hservice"; 
//		ISmsService mc = (ISmsService) factory.create(ISmsService.class, url);
//		Token t=new Token();
//		mc.setNonce(t.getNonce());
//		mc.setSinagure(t.getSinatrue());
//		mc.setTimestamp(t.getTimestamp());
//		mc.send("sd","sddfs");
	}
}
