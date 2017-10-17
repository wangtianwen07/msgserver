package com.css.msgserver.ws.utils;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axiom.soap.SOAPHeaderBlock;

public class HeaderElement {
	String url;
	String cld;
	/**
	 * @param url 地址如：www.csscis.com
	 * @param cld 企业代码如：csscis
	 */
	public HeaderElement(String url,String cld){
		this.url=url;
		this.cld=cld;
	}
	public OMElement getHeader() {
		return getHeader("bank","root","1234556");
	}
	/**
	 * 拼接头文件
	 *<security> 
	 * <username> root </username>
	 * <role> csscis </role> 
	 * <password> password </password>  
	 * </security> 
	 * @param rol 角色编 码 :bank
	 * @param user 用户名:root
	 * @param pwd 密码:123456
	 * @return
	 */
	public OMElement getHeader(String rol,String user,String pwd){
		OMFactory omFactory = OMAbstractFactory.getOMFactory();
		OMNamespace omNS=omFactory.createOMNamespace(url,cld);
		
		//OMElement head = omFactory.createOMElement("Security", omNS);
		OMElement token = omFactory.createOMElement("security", omNS);
		//head.addChild(token);
		OMElement role = omFactory.createOMElement("role", omNS);
		role.addChild(omFactory.createOMText(role, rol));
		token.addChild(role);
		///
		OMElement userName = omFactory.createOMElement("username", omNS);
		userName.addChild(omFactory.createOMText(userName, user));
		token.addChild(userName);
			
		OMElement password = omFactory.createOMElement("password", omNS);
		//password.addAttribute(omFactory.createOMAttribute("type", null, "passwordText"));
		password.addChild(omFactory.createOMText(password, pwd));
		token.addChild(password);
		System.out.println("-----------------"+token.toString());
		return token;
	}
}
