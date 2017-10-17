package com.css.msgserver;

import javax.jws.WebMethod;
import javax.xml.ws.WebServiceClient;

@WebServiceClient(wsdlLocation="${app.ws.bank.url}/services/bocService?wsdl",targetNamespace="http://impl.service.bank.bus.msgserver.css.com")
public interface ICallMsgService {
	@WebMethod(action="exPackage")
	public String exPackage(String str);
}
