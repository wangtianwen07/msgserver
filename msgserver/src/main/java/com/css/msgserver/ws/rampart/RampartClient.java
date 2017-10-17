/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.css.msgserver.ws.rampart;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyEngine;
import org.apache.rampart.RampartMessageData;
import org.springframework.boot.web.servlet.ServletRegistrationBean;

public class RampartClient {

    public static void main(String[] args) throws Exception {
        
        String a0 = "http://127.0.0.1:9988/services/dealService?wsdl";
        String a1= ServletRegistrationBean.class.getResource("/axis2").getPath() + "/conf/axis2.xml";
        String a2= ServletRegistrationBean.class.getResource("/axis2").getPath() + "/policy.xml";
        ConfigurationContext ctx = ConfigurationContextFactory.createConfigurationContextFromFileSystem(a1, null);
        
        ServiceClient client = new ServiceClient(ctx, null);
        Options options = new Options();
        //options.setAction("urn:analyse");
        options.setTo(new EndpointReference(a0));
        options.setProperty(RampartMessageData.KEY_RAMPART_POLICY,  loadPolicy(a2));
        //soap1.2，不设置默认是1.1
        //options.setSoapVersionURI("http://www.w3.org/2003/05/soap-envelope");
        //options.setExceptionToBeThrownOnSOAPFault(false);
        //options.setCallTransportCleanup(true); 
        client.setOptions(options);
        client.engageModule("rampart");
        
        OMElement policy = getPayload("Hello world");
        OMElement response = client.sendReceive(policy);
        System.out.println(response);
    }
    
	private static Policy loadPolicy(String xmlPath) throws Exception {
        StAXOMBuilder builder = new StAXOMBuilder(xmlPath);
        return PolicyEngine.getPolicy(builder.getDocumentElement());
    }
    
    private static OMElement getPayload(String value) {
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace ns = factory.createOMNamespace("http://impl.service.bank.msgserver.css.com","ns1");
        OMElement elem = factory.createOMElement("analyse", ns);//方法名
        OMElement childElem = factory.createOMElement("path", null);//参数名
        childElem.setText(value);
        elem.addChild(childElem);
        return elem;
    }
    
    /**
     * 请求报文范例：
     * <?xml version='1.0' encoding='utf-8'?>
		<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
		<soapenv:Header>
		<wsse:Security xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd" xmlns:wsu="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd" soapenv:mustUnderstand="1">
		<wsu:Timestamp wsu:Id="TS-2744BD79D74981862C15034018487561">
		<wsu:Created>2017-08-22T11:37:28.751Z</wsu:Created>
		<wsu:Expires>2017-08-22T11:42:28.751Z</wsu:Expires>
		</wsu:Timestamp>
		<wsse:UsernameToken wsu:Id="UsernameToken-2744BD79D74981862C15034018487622">
		<wsse:Username>alice</wsse:Username>
		<wsse:Password Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">bobPW</wsse:Password>
		</wsse:UsernameToken>
		</wsse:Security>
		</soapenv:Header>
		<soapenv:Body>
		<ns1:echo xmlns:ns1="http://impl.service.bank.msgserver.css.com">
		<arg>Hello world</arg>
		</ns1:echo>
		</soapenv:Body>
		</soapenv:Envelope>

     */
}
