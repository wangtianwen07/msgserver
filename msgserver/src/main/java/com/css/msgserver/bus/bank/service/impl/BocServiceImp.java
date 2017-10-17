package com.css.msgserver.bus.bank.service.impl;

import java.lang.reflect.UndeclaredThrowableException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.model.BaseXSDataPacket;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.IReturn;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.bus.bank.service.IBocService;
import com.css.msgserver.bus.csscis.service.ICallEbsOpenCardService;
import com.css.msgserver.jpa.pojo.BkB0001Data;
import com.css.msgserver.jpa.service.IBkA0001DataService;
import com.css.msgserver.jpa.service.IBkB0001DataService;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.DecryptException;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.JsonUtil;
import com.css.msgserver.ws.client.WSClientFactory;

import net.sf.json.JSONObject;
@Service("bocService")
public class BocServiceImp implements IBocService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	//
	@Autowired
	private IBkA0001DataService bkA0001DataService;
	//
	@Autowired
	private IBkB0001DataService bkB0001DataService;
	//
	@Override
	public String exPackage(String xml){
		logger.info("接收银行的开卡xml:"+xml);
		StringBuffer sb=new StringBuffer(xml);
		String jydwMy=sb.substring(0, BkBocType.JYDW_MY.length());
		//
		SimpleXSDataPacket lp=new SimpleXSDataPacket();
		//
		if(jydwMy.equals(BkBocType.JYDW_MY)){
			try {
				String dexml=BankBeanUtils.deBocXml(xml);
				BaseXSDataPacket xa=XSDataPacketAdapter.toBean(dexml);
				//
				if(xa.getWHERE().getYWLX().equals(BkBocType.YWLX_FILE_SEND)){
					fileSend(dexml,lp);
				}else if(xa.getWHERE().getYWLX().equals(BkBocType.YWLX_WELFARE)){
					welfare(dexml,lp);
				}else{
					lp.getIRETURN().setIRETURN(IReturn.error_no);
					lp.getIRETURN().setMSGRETURN("业务类型"+xa.getWHERE().getYWLX());
				}
				
			}catch (DecryptException e) {
				logger.error("解密失败："+e.getMessage());
				e.printStackTrace();
				//
				return BkBocType.TDXY_ERR_DE+"解密失败";
				//
			} catch (UndeclaredThrowableException e) {
				logger.error("内部错误：welfare未发布");
				e.printStackTrace();
				//
				return BkBocType.TDXY_ERR_NON+"内部WS调用错误";
				//
			} catch (Exception e) {
				logger.error("未知异常失败："+e.getMessage());
				e.printStackTrace();
				//
				return BkBocType.TDXY_ERR_NON+"未知异常失败";
			}
		}else{
			String logmsg="交易码错误:"+jydwMy;
			logger.error(logmsg);
			return BkBocType.TDXY_ERR_NON+logmsg;
		}
		String re="";
		try {
			re = BankBeanUtils.enBocXml(XSDataPacketAdapter.toXmlStrBuf(lp),BkBocType.TDXY_OK);
		} catch (DecryptException e) {
			logger.info(">>>>>加密失败："+e.getMessage());
			e.printStackTrace();
			return BkBocType.TDXY_ERR_NON+"加密失败";
		}
		return re;
	}
	/**
	 * 文件发送A0001
	 * 实施应答银行，告知已保存反馈内容
	 * @param xml
	 * @return
	 */
	private void fileSend(String xml,SimpleXSDataPacket lp) {
		//1发送文件通知接收,2下载并校验，将结果反馈对方见
		ListXSDataPacket xa=XSDataPacketAdapter.toBean(xml,ListXSDataPacket.class);
		bkA0001DataService.saveBankSendFile(xa);
		//
		lp.getDATASET().setDATA(BankBeanUtils.buildFHBZ(true, "成功！"));
		
	}
	/**
	 * 会员优惠B0001
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	private void welfare(String xml,SimpleXSDataPacket lp) throws Exception {
		SimpleXSDataPacket xa=XSDataPacketAdapter.toBean(xml,SimpleXSDataPacket.class);
		BkB0001Data bd=BankBeanUtils.dePackData(BkB0001Data.class, xa.getDATASET().getDATA());
		//保存请求
		bd.setUuid(IDUtil.getId());
		bkB0001DataService.save(bd);
		//
		JSONObject jo=JSONObject.fromObject(bd);
		//
		ICallEbsOpenCardService ics = WSClientFactory.get(ICallEbsOpenCardService.class);
		//
		JsonUtil.excluds(jo,BankBeanUtils.getNoPackTypeFields(BkB0001Data.class));
		//
		String re=ics.welfare(jo.toString());
		JSONObject rejo=JSONObject.fromObject(re);
		//
		if(rejo.getBoolean(JsonUtil.success)){
			lp.getDATASET().setDATA(BankBeanUtils.buildFHBZ(true, "成功！"));
		}else{
			lp.getDATASET().setDATA(BankBeanUtils.buildFHBZ(false, jo.optString(JsonUtil.msg)));
		}
		//
	}
}
