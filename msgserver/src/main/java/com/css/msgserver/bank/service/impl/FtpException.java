package com.css.msgserver.bank.service.impl;

public class FtpException extends RuntimeException {
	public static String STATE_FILENOTFIND="1";//1文件不存在
	public static String STATE_MD5_ERROR="2";//2文件MD5校验不通过
	public static String STATE_DECODE_ERROR="3";//3文件解密失败
	public static String STATE_EXT_ERROR="4";//4文件格式不符
	public static String STATE_NAME_ERROR="5";//5 文件名校验不通过
	public String state;//文件异常
	public FtpException(String msg,String state){
		super(msg);
		this.state=state;
	}
}
