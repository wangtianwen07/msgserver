package com.css.msgserver.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.css.msgserver.jpa.pojo.BkDictConvert;

public interface BkDictConvertDao extends CrudRepository<BkDictConvert, String>{
	public BkDictConvert findByDictNameAndSysVal(String dictName,String sysVal);
}
