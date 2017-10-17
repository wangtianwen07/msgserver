package com.css.msgserver.csscis.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.msgserver.csscis.hession.service.ISmsService;
@Controller
@RequestMapping("/sms")
public class SmsController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	@Autowired
	private ISmsService smsService;
	@RequestMapping("/send")
    @ResponseBody
	public String send(String tel,String msg){
		System.out.println("send....");
		logger.info("send:"+msg);
		//smsService.send(tel, msg);
		//Axis2RPCFactory.test();
		return "{success:true,msg:'成功'}";
	}
}
