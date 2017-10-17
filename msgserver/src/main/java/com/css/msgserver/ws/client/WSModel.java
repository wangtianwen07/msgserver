package com.css.msgserver.ws.client;

import java.lang.annotation.Annotation;

import javax.xml.ws.WebServiceClient;

public class WSModel<T> {
	public static final String TYPE_AXIS2LOGIN="axis2login";
	public static final String TYPE_SIMPLE="simple";
	private String username;
	private String password;
	private String role;
	private String type;
	private String wsdlLocation;
	private String targetNamespace;
	private boolean wsModelAble=false;
	Class<T> model;
	public WSModel(Class<T> clz){
		model=clz;
		WebServiceClient ws=getAnt(clz,WebServiceClient.class);
		//
		if(ws!=null){
			wsModelAble= true;
		}
		//
		wsdlLocation=ws.wsdlLocation();
		targetNamespace=ws.targetNamespace();
		//取第一个
		String urlparam=ParseKeyword.getKeywords(wsdlLocation).get(0);
		//
		String roleparam=urlparam.substring(0,urlparam.lastIndexOf("."))+".role";
		role=ParseKeyword.getSysConf(roleparam);
		//
		String usernameparam=urlparam.substring(0,urlparam.lastIndexOf("."))+".username";
		username=ParseKeyword.getSysConf(usernameparam);
		//
		String passwordparam=urlparam.substring(0,urlparam.lastIndexOf("."))+".password";
		password=ParseKeyword.getSysConf(passwordparam);
		//
		String typeparam=urlparam.substring(0,urlparam.lastIndexOf("."))+".type";
		type=ParseKeyword.getSysConf(typeparam);
		//
		wsdlLocation=ParseKeyword.parseValue(wsdlLocation);//填充url
	}
	public boolean isModel(){
		return wsModelAble;
	}
	private <F extends Annotation> F  getAnt(Class<T> clz,Class<F> annotion){
		if(clz.isAnnotationPresent(annotion)){
			return clz.getAnnotation(annotion);
		}
		return null;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getWsdlLocation() {
		return wsdlLocation;
	}
	public void setWsdlLocation(String wsdlLocation) {
		this.wsdlLocation = wsdlLocation;
	}
	public String getTargetNamespace() {
		return targetNamespace;
	}
	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
