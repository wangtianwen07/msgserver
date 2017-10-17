package com.css.msgserver.jpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkA0001Ywsjj;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.repository.BkA0001DataDao;
import com.css.msgserver.jpa.repository.BkA0001YwsjjDao;
import com.css.msgserver.jpa.service.IBkA0001DataService;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.PackDataException;
@Service("bkA0001DataService")
@Transactional
public class BkA0001DataServiceImp implements IBkA0001DataService {
	@Autowired
	private BkA0001DataDao bkA0001DataDao;
	@Autowired
	private BkA0001YwsjjDao bkA0001YwsjjDao;
	@Transactional(readOnly=true)
	@Override
	public List<BkA0001Data> getUnDownloadData() {
		List<BkA0001Data> bkd=bkA0001DataDao.findBySendStateAndCzlxAndSender(SendState.dcl,BkBocType.CZLX_FILE_SEND,BkBocType.JYDW_BK_BOC);
		return bkd;
	}
	@Override
	public List<BkA0001Data> getSendOpenCardDataValidate() {
		List<BkA0001Data> bkd=bkA0001DataDao.findBySendStateAndCzlxAndSender(SendState.dcl,BkBocType.CZLX_FILE_DOWN_VALIDATE,BkBocType.JYDW_BK_BOC);
		return bkd;
	}
	@Transactional(readOnly=true)
	@Override
	public List<BkA0001Data> getOpenCardData(String sendState,String czlx,String sender,int size) {
		Pageable pageable =new PageRequest(0, size);
		Page<BkA0001Data> bkd=bkA0001DataDao.findBySendStateAndCzlxAndSender(sendState,czlx,sender,pageable);
		return bkd.getContent();
	}
	@Override
	public void lock(List<BkA0001Data> bd) {
		updateSendState(bd,SendState.LC);
	}
	@Override
	public void unlock(List<BkA0001Data> bd,String sendState) {
		updateSendState(bd,sendState);
	}
	@Override
	public void unlock(BkA0001Data bd,String sendState) {
		updateSendState(bd,sendState);
	}
	private void updateSendState(List<BkA0001Data> bd,String sendState){
		for(BkA0001Data b:bd){
			updateSendState(b,sendState);
		}
	}
	private void updateSendState(BkA0001Data bd,String sendState){
			bd.setSendState(sendState);
			bkA0001DataDao.save(bd);
	}
	@Override
	public void saveBankSendFile(ListXSDataPacket pd) throws PackDataException {
		String packData=pd.getDATASET().getDATA();
		BkA0001Data bd=BankBeanUtils.dePackData(BkA0001Data.class, packData);
		bd.setUuid(IDUtil.getId());
		bd.setSender(BkBocType.JYDW_BK_BOC);
		bd.setSendState(SendState.dcl);
		bkA0001DataDao.save(bd);
		//
		List<String> ywsjj=pd.getDATASET().getYWSJJ();
		for(String yw:ywsjj){
			BkA0001Ywsjj jj=BankBeanUtils.dePackData(BkA0001Ywsjj.class, yw);
			jj.setUuid(IDUtil.getId());
			jj.setBkA0001DataUuid(bd.getUuid());
			bkA0001YwsjjDao.save(jj);
		}
	}
	@Override
	public int countOpenCardData(String sendState, String czlx, String sender) {
		return bkA0001DataDao.countBySendStateAndCzlxAndSender(sendState, czlx, sender);
	}

}
