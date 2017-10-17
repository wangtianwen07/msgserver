package com.css.msgserver.bank.model;

public class IReturn {
	public static final String success="S00000";//成功
	public static final String error_yw="E00001";//业务异常
	public static final String error_no="E00002";//交易类型不存在
	public static final String error_lan="E00003";//交易关闭
	public static final String error_cd="E00004";//非交易时间
	public static final String error_xml_gs="E99999";//未知系统错误
}