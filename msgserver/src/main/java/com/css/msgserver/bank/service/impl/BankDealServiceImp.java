package com.css.msgserver.bank.service.impl;

import org.springframework.stereotype.Service;

import com.css.msgserver.bank.service.IDealServcie;
/**
 * @author pc
 *银行交易处理结果主类
 */
@Service("dealService")
public class BankDealServiceImp implements IDealServcie {

	/**
	 *测试方法用于 rampart
	 * 
	 */
	@Override
	public void analyse(String path) {
		System.out.println("BankDealServiceImp分析方法:"+path);
	}
}
