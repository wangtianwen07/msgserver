package com.css.msgserver.bank.model;

public class BkBocType {
	/**
	 *业务类型 
	 * 
	 */
	public static final String YWLX_FILE_SEND="A0001";//文件发送
	public static final String YWLX_WELFARE="B0001";//优惠
	/**
	 * 
	 * 2.2.3	交易单位列表
	 * KA01	银行系统
	 * KA02	工会卡系统
	 */
	public static final String JYDW_MY="S001";
	public static final String JYDW_BK_BOC="KA01";
	/**
	 * 
	 *操作类型 
	 * 
	 * 
	 */
	public static final String CZLX_FILE_SEND="1";//发送文件，通知对方接收
	public static final String CZLX_FILE_DOWN_VALIDATE="2";//下载并校验，将结果反馈对方
	public static final String CZLX_WELFARE_VALIDATE="1";//校验会员优惠权限
	public static final String CZLX_WELFARE_USED="2";//通知工会会员已使用该优惠
	/**
	 * 
	 *制证类别 
	 * 
	 */
	public static final String ZZLB_NEW_SHENZHEN_TONG="00";//有深圳通
	public static final String ZZLB_NEW_UN_SHENZHEN_TONG="01";//无深圳通
	/**
	 * 
	 *返回标志 
	 */
	public static final String FHBZ_OK="1";
	public static final String FHBZ_ERROR="0";
	/**
	 * 
	 * 接收文件失败，反馈对方时填写
	 * 
	 */
	public static final String FLAG_OK="1";//1 接收成功
	public static final String FLAG_ERROR="0";//0 接收失败
	/**
	 * 
	 * 关联方式
	 * 
	 */
	public static final String GLFS_ZJLX="1";//使用证件类型
	public static final String GLFS_GHH="2";//使用工会号
	/**
	 * 
	 *通道响应码 
	 * 
	 */
	public static final String TDXY_OK="00";//使用证件类型
	public static final String TDXY_ERR_DE="01";//解密异常
	//解析xml异常
	public static final String TDXY_ERR_XML="02";
	//其他异常
	public static final String TDXY_ERR_NON="99";
}
