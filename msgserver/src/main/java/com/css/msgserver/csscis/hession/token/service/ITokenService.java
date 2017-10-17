package com.css.msgserver.csscis.hession.token.service;
public interface ITokenService {
	public static final int SUCCESS = 1; 
	public static final int INVALID = 2; 
	public static final int FAILED = 3; 
	public static final int ERROR = 4; 
	//安全签名加密参数  
    public void setSinagure(String sinatrue);  
    public void setNonce(String nonce);  
    public void setTimestamp(String timestamp); 
    public boolean validate();
}
