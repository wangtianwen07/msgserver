package com.css.msgserver.bank.service;

import java.io.File;
import java.util.List;

import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkOpenCardResult;

public interface IOpenCardResultService {
	
	
	/**
	 *下载文档数据并做验证
	 * @param page
	 * @param sendState
	 */
	public void downOpenCardResult(BkA0001Data bkd) throws Exception;
	/**
	 *文档数据入库 
	 * 
	 */
	public void saveOpenCardResult(File file)throws Exception;
	/**
	 *文档下载结果返馈给银行 
	 * 
	 * 
	 */
	public void sendBankDownResult(List<BkA0001Data> a0001Data) throws Exception;
	/**
	 *文开卡结果返馈给业系统 
	 * 
	 */
	public void sendOpenCardResult(List<BkOpenCardResult> openCardResult) throws Exception;
	void lock(List<BkOpenCardResult> bd);
	void unlock(List<BkOpenCardResult> bd, String sendState);
	void unlock(BkOpenCardResult bd, String sendState);
	List<BkOpenCardResult> getOpenCardResult(String sendState, int size);
	int countOpenCardResult(String sendState);
}