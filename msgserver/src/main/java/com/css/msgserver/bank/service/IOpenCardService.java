package com.css.msgserver.bank.service;

import java.util.List;

import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkOpenCard;

public interface IOpenCardService {
	
	
	/**
	 * 解锁数据
	 * @param page
	 * @param sendState
	 */
	public void relock(List<BkOpenCard> page,String sendState);
	/**
	 * 生成文档
	 * @param path 指定生成目录
	 */
	public void produceOpenCardFile(List<BkOpenCard> page,String path)throws Exception;
	/**
	 * 
	 *发送通知 
	 * @param path FTP文件路径
	 * @param names 需要发送银行的通知
	 * @throws Exception
	 */
	public void sendOpenCardData(String path,List<BkA0001Data> a0001Data) throws Exception;
	
	/**
	 * 锁数据
	 * @param page
	 * @param sendState
	 */
	void lock(List<BkOpenCard> page);
	/**
	 * 得到待开卡数据
	 * @param zzlb  制证类别
	 * @return
	 */
	List<BkOpenCard> getOpenCard(String zzlb,String sendState);
	/**
	 * 开卡数据发送结果处理
	 * @param bd
	 */
	void sendOpenCardDataValidate(BkA0001Data bd);
}
