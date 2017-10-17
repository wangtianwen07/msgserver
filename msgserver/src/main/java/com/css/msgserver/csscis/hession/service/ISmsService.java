package com.css.msgserver.csscis.hession.service;

import com.css.msgserver.csscis.hession.token.service.ITokenService;

public interface ISmsService extends ITokenService{
	public int send(String tel,String msg);
}
