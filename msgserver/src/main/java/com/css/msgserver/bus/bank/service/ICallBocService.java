package com.css.msgserver.bus.bank.service;

import javax.jws.WebMethod;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(wsdlLocation="${app.ws.bank.url}/services/openCardService?wsdl",targetNamespace="http://impl.service.csscis.bus.msgserver.css.com")
public interface ICallBocService {
	@WebMethod(action="send")
	public String send(String str);
}
