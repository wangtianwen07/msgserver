package com.css.msgserver.csscis.hession.token.service.impl;

import com.css.msgserver.csscis.hession.token.service.ITokenService;
import com.css.msgserver.csscis.hession.token.service.Token;

public class TokenServiceImp implements ITokenService{
	protected String sinatrue;
	protected String nonce;
	protected String timestamp;
	@Override
	public void setSinagure(String sinatrue) {
		this.sinatrue=sinatrue;
	}

	@Override
	public void setNonce(String nonce) {
		this.nonce=nonce;
	}

	@Override
	public void setTimestamp(String timestamp) {
		this.timestamp=timestamp;
	}

	@Override
	public boolean validate() {
		Token t=new Token(timestamp,nonce,sinatrue);
		return t.validateSignature();
	}
}
