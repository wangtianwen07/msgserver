package com.css.msgserver.utils;

import java.util.List;

import net.sf.json.JSONObject;

public class JsonUtil {
	public static final String success="success";
	public static final String msg="msg";
	public static final String data="data";
	public static JSONObject toSuccess(String m,Object bean){
		JSONObject jo=new JSONObject();
		jo.put(success,true);
		jo.put(msg,m);
		if(bean!=null){
			jo.put(data,JSONObject.fromObject(bean).toString());
		}
		return jo;
	}
	public static JSONObject toSuccess(String msg){
		return toSuccess(msg,null);
	}
	public static String toSuccessStr(String msg,Object bean){
		return toSuccess(msg,bean).toString();
	}
	public static String toSuccessStr(String msg){
		return toSuccess(msg).toString();
	}
	public static JSONObject toError(String msg,Object bean){
		JSONObject jo=toSuccess(msg,bean);
		jo.put(success,false);
		return jo;
	}
	public static String toErrorStr(String msg,Object bean){
		return toError(msg,bean).toString();
	}
	public static JSONObject toError(String msg){
		return toError(msg,null);
	}
	public static String toErrorStr(String msg){
		return toError(msg).toString();
	}
	public static JSONObject excluds(JSONObject jo,List<String> exs){
		for(String k:exs){
			jo.remove(k);
		}
		return jo;
	}
}
