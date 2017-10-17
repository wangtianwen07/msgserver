package com.css.msgserver.jpa.service;

import java.util.List;

import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.utils.PackDataException;

public interface IBkA0001DataService {
	/**
	 * 保存需要处理的文档
	 * @param pd
	 * @throws PackDataException
	 */
	public void saveBankSendFile(ListXSDataPacket pd) throws PackDataException;
	
	
	/**
	 * 得到未下载的数据列表
	 * @return
	 */
	public List<BkA0001Data> getUnDownloadData();


	void lock(List<BkA0001Data> bd);


	void unlock(List<BkA0001Data> bd, String sendState);


	void unlock(BkA0001Data bd, String sendState);


	List<BkA0001Data> getOpenCardData(String sendState, String czlx, String sender, int size);
	int countOpenCardData(String sendState, String czlx, String sender);


	/**
	 * 银行下载文件异常处理
	 * 【
		文件A不存在
		文件A校验不通过
		文件A格式不符
	 * 】
	 * @return
	 */
	List<BkA0001Data> getSendOpenCardDataValidate();
}
