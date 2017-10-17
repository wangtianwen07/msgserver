package com.css.msgserver.bus.csscis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.bus.csscis.service.OpenCardService;
import com.css.msgserver.jpa.pojo.BkOpenCard;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.repository.BkOpenCardDao;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.JsonUtil;

import net.sf.json.JSONObject;
@Service("wsOpenCardService")
public class WsOpenCardServiceImp implements OpenCardService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BkOpenCardDao bkOpenCardDao;
	@Override
	public String open(String str) {
		logger.info(str);
		JSONObject jo=JSONObject.fromObject(str);
		BkOpenCard bkc=(BkOpenCard)JSONObject.toBean(jo, BkOpenCard.class);
		logger.info("SecCountry>>"+bkc.getSecCountry());
		bkc.setUuid(IDUtil.getId());
		bkc.setSendState(SendState.ZB);
		bkOpenCardDao.save(bkc);
		return JsonUtil.toSuccessStr("成功！");
	}
	/* (non-Javadoc)
	 * @see com.css.msgserver.csscis.service.OpenCardService#send(java.lang.String)
	 * <!-- 本地测试接口，用于测试银行接收报文 -->
	 */
	@Override
	public String send(String str) {
		logger.info("测试银行接收----"+str);
		SimpleXSDataPacket lp=new SimpleXSDataPacket();
		lp.getDATASET().setDATA(BankBeanUtils.buildFHBZ(true, "我是银行成功！"));
		String xml="";
		try {
			xml = BankBeanUtils.enBocXml(XSDataPacketAdapter.toXmlStrBuf(lp),BkBocType.TDXY_OK);
		} catch (Exception e) {
			logger.info("解密失败:"+e.getMessage());
			e.printStackTrace();
		}
		return xml;
	}
}
