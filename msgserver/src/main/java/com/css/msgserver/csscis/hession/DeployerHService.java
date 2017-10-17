package com.css.msgserver.csscis.hession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.stereotype.Component;

import com.css.msgserver.csscis.hession.service.ISmsService;
@Component
public class DeployerHService {
	@Autowired
	private ISmsService smsService;
	@Bean(name="/sms/hservice")
	public HessianServiceExporter hservice(){
		HessianServiceExporter exporter = new HessianServiceExporter();
	    exporter.setService(smsService);
	    exporter.setServiceInterface(ISmsService.class);
	    return exporter;
	}
}
