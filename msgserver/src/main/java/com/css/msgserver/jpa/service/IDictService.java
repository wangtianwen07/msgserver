package com.css.msgserver.jpa.service;

import com.css.msgserver.jpa.pojo.BkDictConvert;

public interface IDictService {
	public void save(BkDictConvert bc);
	
	public BkDictConvert get(String dictName,String sysVal);
}
