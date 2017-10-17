package com.css.msgserver.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.msgserver.jpa.pojo.BkB0001Data;
import com.css.msgserver.jpa.repository.BkB0001DataDao;
import com.css.msgserver.jpa.service.IBkB0001DataService;
@Service("bkB0001DataService")
@Transactional
public class BkB0001DataServiceImp implements IBkB0001DataService {
	@Autowired
	private BkB0001DataDao bkB0001DataDao;
	@Override
	public void save(BkB0001Data bd) {
		bkB0001DataDao.save(bd);
	}

}
