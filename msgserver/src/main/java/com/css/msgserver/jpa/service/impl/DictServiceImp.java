package com.css.msgserver.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.msgserver.jpa.pojo.BkDictConvert;
import com.css.msgserver.jpa.repository.BkDictConvertDao;
import com.css.msgserver.jpa.service.IDictService;
@Service("dictService")
@Transactional
public class DictServiceImp implements IDictService {
	@Autowired
	private BkDictConvertDao bkDictConvertDao;
	public void save(BkDictConvert bc) {
		bkDictConvertDao.save(bc);
	}
	@Transactional(readOnly=true)
	public BkDictConvert get(String dictName, String sysVal) {
		
		return bkDictConvertDao.findByDictNameAndSysVal(dictName, sysVal);
	}
	@Transactional(readOnly=true)
	public String getBkVal(String dictName, String sysVal) {
		BkDictConvert dc=get(dictName,sysVal);
		return dc.getBkVal();
	}
}
