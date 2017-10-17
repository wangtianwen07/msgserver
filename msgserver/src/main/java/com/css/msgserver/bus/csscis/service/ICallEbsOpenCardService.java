package com.css.msgserver.bus.csscis.service;

import javax.jws.WebMethod;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(wsdlLocation="${ebs.ws.csscis.url}/ebs/services/openCardService?wsdl",targetNamespace="http://imp.hy.csscis.bus.common.css.com")
public interface ICallEbsOpenCardService {
	@WebMethod(action="openCardResult")
	public String openCardResult(String str);
	
	
	
	@WebMethod(action="welfare")
	public String welfare(String str);
}