package com.css.msgserver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.ftp.FtpConnFactory;
import com.css.msgserver.bank.local.LocationFileProcessor;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.bank.service.IOpenCardService;
import com.css.msgserver.bank.service.impl.BankTextWriter;
import com.css.msgserver.bank.service.impl.BocDataPacketBuilder;
import com.css.msgserver.bank.service.impl.FtpException;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkA0001Ywsjj;
import com.css.msgserver.jpa.pojo.BkB0001Data;
import com.css.msgserver.jpa.pojo.BkDictConvert;
import com.css.msgserver.jpa.pojo.BkOpenCard;
import com.css.msgserver.jpa.pojo.BkOpenCardResult;
import com.css.msgserver.jpa.pojo.PackType;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.repository.BkOpenCardDao;
import com.css.msgserver.jpa.service.IDictService;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.FileUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.Md5Utils;
import com.css.msgserver.utils.TimeUtils;
import com.css.msgserver.ws.client.WSClientFactory;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringBootTest(classes=App.class)// 指定spring-boot的启动类   
public class JpaTest {
	private static Logger logger = LoggerFactory.getLogger( LocationFileProcessor.class );
	@Autowired
	private IDictService dictService;
	@Autowired
	FtpConnFactory ftpConnFactory;
	@Autowired
	private IOpenCardService openCardService;
	@Autowired
	private BkOpenCardDao bkOpenCardDao;
	@Autowired
	private BocDataPacketBuilder bocDataPacketBuilder;
	@Test
	public void testDict(){
		BkDictConvert entity=new BkDictConvert();
		entity.setUuid("test1");
		entity.setBkName("我的");
		entity.setBkVal("1");
		entity.setSysVal("a");
		entity.setSysName("我的");
		dictService.save(entity);
	}
	@Test
	public void testA001(){
		BkA0001Data entity=new BkA0001Data();
		entity.setCreateTime(new Date());
		entity.setUuid("abc");
		entity.setCzlx("3");
		entity.setRemark("333");
		entity.setWjs(3);
		
	}
	@Test
	public void testOpenCard(){
		List<BkOpenCard> bocs=openCardService.getOpenCard(BkBocType.ZZLB_NEW_SHENZHEN_TONG,SendState.ZB);
		System.out.println("testOpenCard>>>>>>>>>>>"+bocs.size());
	}
	@Test
	public void testFtp(){
		try {
			FTPClient co=ftpConnFactory.getConnect();
			//FtpService fs=new FtpService();
			//fs.setClient(co);
			String[] s=co.listNames();
			System.out.println("--------------------"+s.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testDate() throws Exception{
		System.out.println("testOpenCard>>>>>>>>>>>"+TimeUtils.get0Daystamp());
		System.out.println("testOpenCard>>>>>>>>>>>"+TimeUtils.get24Daystamp());
	}
	@Test
	public void testJYlsh() throws InterruptedException{
		for(int i=0;i<10;i++){
			Thread.sleep(1000*10);
			System.out.println("testOpenCard>>>>>>>>>>>"+IDUtil.getBocJylsh("10"));
		}
	}
	/**
	 * 生成文件
	 * @throws Exception
	 */
	@Test
	public void testOpencardResult()throws Exception{
		BkOpenCard bocs=bkOpenCardDao.findOne("432a2281c0a34fefb4e2b91474a5c28e");
		String filename=bocs.getFileName();
		//result
		StringBuffer r=new StringBuffer(filename);
		r.replace(12, 13, "1");
		filename=r.toString();
		String ljm="d:/bank/send/"+filename;
		//
		BankTextWriter btw = new BankTextWriter(ljm);
		System.out.println(ljm);
		btw.open();
		int i=0;
		BkOpenCardResult br=new BkOpenCardResult();
		br.setSeqNo(bocs.getSeqNo());
		br.setCardType(bocs.getCardType());
		br.setFirstName(bocs.getFirstName());
		br.setLastName(bocs.getLastName());
		br.setCusSex("1");
		br.setCardId(bocs.getCardId());
		br.setCardNo("6000012423131"+i);
		br.setActType("1");
		br.setRtnCode("0000");
		br.setRtnMsg("成功");
		btw.write(BankBeanUtils.enPackData(br));
		btw.close();
		System.out.println(ljm);
		FileUtils.enFile(new File(ljm));
	}
	/**
	 *通知接收 
	 */
	@Test
	public void testOpencardResultCallWs()throws Exception{
		ICallMsgService ics = WSClientFactory.get(ICallMsgService.class);
		BkOpenCard bocs=bkOpenCardDao.findOne("fb5cbf773b9c4fe698e46e3f02f3e3c1");
		String filename=bocs.getFileName();
		//result
		StringBuffer r=new StringBuffer(filename);
		r.replace(12, 13, "1");
		filename=r.toString();
		String ljm="d:/bank/send/";
		//
		BkA0001Data bd=new BkA0001Data();
		bd.setCzlx(BkBocType.CZLX_FILE_SEND);
		bd.setWjs(1);
		//
		BkA0001Ywsjj jj=new BkA0001Ywsjj();
		jj.setWjm(filename);
		jj.setLjm("/bank/send");
		jj.setKkpc(1);
		jj.setZklx(BkBocType.ZZLB_NEW_SHENZHEN_TONG);
		jj.setJym(Md5Utils.getMd5byFile(new File(ljm+"/done/"+filename)));
		//
		List<BkA0001Ywsjj> as=new ArrayList<BkA0001Ywsjj>();
		as.add(jj);
		//S001+密文。
		StringBuffer str = new StringBuffer();
		// ftp服务器上文件存在
		ListXSDataPacket pack = bocDataPacketBuilder.buildPacket(bd, as,PackType.in);
		// 打包数据
		str.append(XSDataPacketAdapter.toXmlStrBuf(pack));
		//加密并发送数据
		String bankre=ics.exPackage(BankBeanUtils.enBocXml(str));
		System.out.println(bankre);
	}
	/**
	 *通知接收 失败
	 */
	@Test
	public void testOpencardResultErrorCallWs()throws Exception{
		ICallMsgService ics = WSClientFactory.get(ICallMsgService.class);
		BkOpenCard bocs=bkOpenCardDao.findOne("432a2281c0a34fefb4e2b91474a5c28e ");
		String filename=bocs.getFileName();
		//result
		StringBuffer r=new StringBuffer(filename);
		//r.replace(12, 13, "1");
		filename=r.toString();
		String ljm="d:/bank/send/";
		//
		BkA0001Data bd=new BkA0001Data();
		bd.setCzlx(BkBocType.CZLX_FILE_DOWN_VALIDATE);
		bd.setWjs(1);
		//
		BkA0001Ywsjj jj=new BkA0001Ywsjj();
		jj.setWjm(filename);
		jj.setLjm("/bank/send");
		jj.setKkpc(1);
		jj.setZklx(BkBocType.ZZLB_NEW_SHENZHEN_TONG);
		jj.setJym(Md5Utils.getMd5byFile(new File(ljm+"/done/"+filename)));
		jj.setSbyy(FtpException.STATE_EXT_ERROR);
		jj.setFlag(BkBocType.FLAG_ERROR);
		//
		List<BkA0001Ywsjj> as=new ArrayList<BkA0001Ywsjj>();
		as.add(jj);
		//S001+密文。
		StringBuffer str = new StringBuffer();
		// ftp服务器上文件存在
		ListXSDataPacket pack = bocDataPacketBuilder.buildPacket(bd, as,PackType.out);
		// 打包数据
		str.append(XSDataPacketAdapter.toXmlStrBuf(pack));
		//加密并发送数据
		String bankre=ics.exPackage(BankBeanUtils.enBocXml(str));
		System.out.println(bankre);
	}
	/**
	 *通知接收 失败
	 */
	@Test
	public void testWelfare()throws Exception{
		ICallMsgService ics = WSClientFactory.get(ICallMsgService.class);
		//
		BkB0001Data bd=new BkB0001Data();
		bd.setCzlx(BkBocType.CZLX_WELFARE_USED);
		bd.setGlfs(BkBocType.GLFS_GHH);
		bd.setZjlx("01");
		bd.setZjhm("142255478950123654");
		bd.setYhdm("AXDFWE12456854");
		//
		//S001+密文。
		StringBuffer str = new StringBuffer();
		// ftp服务器上文件存在
		SimpleXSDataPacket pack = bocDataPacketBuilder.buildPacket(bd);
		// 打包数据
		str.append(XSDataPacketAdapter.toXmlStrBuf(pack));
		//加密并发送数据
		String bankre="";
		try {
			bankre = ics.exPackage(BankBeanUtils.enBocXml(str));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(bankre);
	}
	@Test
	public void testDebug(){
		System.out.println(logger.isInfoEnabled());
	}
}
