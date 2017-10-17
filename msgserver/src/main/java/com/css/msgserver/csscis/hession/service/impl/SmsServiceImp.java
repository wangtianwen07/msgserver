package com.css.msgserver.csscis.hession.service.impl;

import org.springframework.stereotype.Service;

import com.css.msgserver.csscis.hession.service.ISmsService;
import com.css.msgserver.csscis.hession.token.service.impl.TokenServiceImp;
@Service("SmsService")
public class SmsServiceImp  extends TokenServiceImp implements ISmsService{

	@Override
	public int send(String tel, String msg) {
		System.out.println(">>>:"+tel+",msg:"+msg);
		return SUCCESS;
	}

}
