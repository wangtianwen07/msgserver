package com.css.msgserver.bank.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.css.msgserver.bank.model.AbstracXSDataPacket;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkA0001Ywsjj;
import com.css.msgserver.jpa.pojo.BkB0001Data;
import com.css.msgserver.jpa.pojo.PackType;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.ws.utils.WsAxis2User;

@Component
public class BocDataPacketBuilder {
	@Value("${css.server.ip}")
	private String serverip;
	@Value("${css.server.id}")
	private String serverid;
	private void buildPacketHead(String ywlx,AbstracXSDataPacket bdp){
		
		WsAxis2User wa=new WsAxis2User();
		bdp.setSAFEINFO("V1.0|"+serverip+"|"+wa.get("app.ws.bank.username")+"|"+wa.get("app.ws.bank.password"));
		bdp.getWHERE().setYWLX(ywlx);
		//
		bdp.getWHERE().setJYLSH(IDUtil.getBocJylsh(serverid));
		//
	}
	/**
	 * 创建文件发送包
	 * @param n 数据包
	 * @param ls  数据项
	 * @return
	 */
	public ListXSDataPacket buildPacket(BkA0001Data n,List<BkA0001Ywsjj> ls)throws Exception{
		return buildPacket(n,ls,PackType.inout);	
	}
	public ListXSDataPacket buildPacket(BkA0001Data n,List<BkA0001Ywsjj> ls,PackType type)throws Exception{
		ListXSDataPacket bdp=new ListXSDataPacket();
		//
		buildPacketHead(BkBocType.YWLX_FILE_SEND,bdp);
		//
		bdp.getDATASET().setDATA(BankBeanUtils.enPackData(n,type));
		for(BkA0001Ywsjj ay:ls){
			bdp.getDATASET().getYWSJJ().add(BankBeanUtils.enPackData(ay,type));
		}
		return bdp;
	}
	/**
	 * 创建文件发送包
	 * @param n 数据包
	 * @param ls  数据项
	 * @return
	 */
	public SimpleXSDataPacket buildPacket(BkB0001Data n)throws Exception{
		return buildPacket(n,PackType.inout);	
	}
	public SimpleXSDataPacket buildPacket(BkB0001Data n,PackType type)throws Exception{
		SimpleXSDataPacket bdp=new SimpleXSDataPacket();
		buildPacketHead(BkBocType.YWLX_WELFARE,bdp);
		//
		bdp.getDATASET().setDATA(BankBeanUtils.enPackData(n,type));
		//
		return bdp;
	}
}
