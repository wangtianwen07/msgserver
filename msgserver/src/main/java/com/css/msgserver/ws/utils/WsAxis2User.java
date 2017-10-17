package com.css.msgserver.ws.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.axis2.AxisFault;

public class WsAxis2User {
	private static Map<String,String> users;
	public void setConf(){
		Properties pro = new Properties();
		if(users==null){
			try {
				InputStream is=WsAxis2User.class.getResourceAsStream("/axis2/axis2-user.properties");
				pro.load(is);
				users=new HashMap<String,String>();
				for(String key:pro.stringPropertyNames()){
					users.put(key, pro.getProperty(key));
				}
				is.close();
			} catch (IOException e) {
				new AxisFault("axis1-user.properties未设置用户名/密码!");
			}
		}
		return;
	}
	public  String get(String key){
		if(users==null){
			setConf();
		}
		return users.get(key);
	}
}
