package com.css.msgserver.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.model.BaseXSDataPacket;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.conf.BankConf;

@Controller
@RequestMapping("/bank")
public class BankController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	@RequestMapping("/test")
    @ResponseBody
	public String test(){
		System.out.println("test....");
		
		System.out.println("ok");
		return "ok";
	}
	@RequestMapping("/testanswer")
    @ResponseBody
	public String testanswer(){
		
		String re="-------";
		try {
			//AbstractDataPacket s = DataPacketAdapter.buildDataPacket(BankConf.getRealFile("file_send_to_bank_answer.xml"));
			//BankAnswerXSDataPacket s=XSDataPacketAdapter.toBean(BankConf.getRealFile("file_send_to_bank_answer.xml"), BankAnswerXSDataPacket.class);
			SimpleXSDataPacket bdp=new SimpleXSDataPacket();
			bdp.setSAFEINFO("asdf");
			bdp.getDATASET().setDATA("12212|2323|fwew|2342|sfd");
			System.out.println(XSDataPacketAdapter.toXmlStr(bdp));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(re);
		return re;
	}
	@RequestMapping("/testsend")
    @ResponseBody
	public String testsend(){
		
		String re="-------";
		try {
			//AbstractDataPacket s = DataPacketAdapter.buildDataPacket(BankConf.getRealFile("file_send_to_bank_answer.xml"));
			ListXSDataPacket bdp=new ListXSDataPacket();
			bdp.setSAFEINFO("asdf");
			bdp.getDATASET().setDATA("223233|).setCZLX(");
			
			System.out.println("-----------包------");
			System.out.println(XSDataPacketAdapter.toXmlStr(bdp));
			System.out.println("-----------解------");
			ListXSDataPacket s=XSDataPacketAdapter.toBean(BankConf.getRealFile("file_send_to_bank_send.xml"), ListXSDataPacket.class);
			System.out.println("-----------getSAFEINFO------"+s.getSAFEINFO());
			System.out.println("-----------getFHJYM------"+s.getDATASET().getYWSJJ().get(0));
			//
			System.out.println("-----------base------");
			BaseXSDataPacket bs=XSDataPacketAdapter.toBean(BankConf.getRealFile("file_send_to_bank_send.xml"), BaseXSDataPacket.class);
			System.out.println("-----------getSAFEINFO------"+bs.getSAFEINFO());
			System.out.println("-----------getYWLX------"+bs.getWHERE().getYWLX());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(re);
		return re;
	}
}
