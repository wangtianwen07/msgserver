package com.css.msgserver.jpa.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.msgserver.bank.adapter.IProcessor;
import com.css.msgserver.jpa.pojo.BkOpenCardResult;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.repository.BkOpenCardResultDao;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.IDUtil;

@Service("openCardResultProcessService")
@Transactional
public class OpenCardResultProcessServiceImp implements IProcessor {
	private static Logger logger = LoggerFactory.getLogger(OpenCardResultProcessServiceImp.class);
	@Autowired
	private BkOpenCardResultDao bkOpenCardResultDao;

	@Override
	public String[] doProcessor(String[] val, File file) {
		//
		if (logger.isDebugEnabled()) {
			for (String v : val) {
				logger.info(v);
			}
		}
		// 保存
		BkOpenCardResult ocr = BankBeanUtils.dePackData(BkOpenCardResult.class, val);
		//
		ocr.setUuid(IDUtil.getId());
		ocr.setSendState(SendState.DF);
		ocr.setFileName(file.getName());
		bkOpenCardResultDao.save(ocr);
		return val;
	}

}
