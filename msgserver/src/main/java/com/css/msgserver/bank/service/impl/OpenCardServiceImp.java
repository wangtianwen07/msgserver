package com.css.msgserver.bank.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.ftp.FtpConnFactory;
import com.css.msgserver.bank.ftp.FtpService;
import com.css.msgserver.bank.model.AnswerData;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.bank.service.IOpenCardService;
import com.css.msgserver.bus.bank.service.ICallBocService;
import com.css.msgserver.conf.BankConf;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkA0001Ywsjj;
import com.css.msgserver.jpa.pojo.BkOpenCard;
import com.css.msgserver.jpa.pojo.PackType;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.repository.BkA0001DataDao;
import com.css.msgserver.jpa.repository.BkA0001YwsjjDao;
import com.css.msgserver.jpa.repository.BkOpenCardDao;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.FileUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.Md5Utils;
import com.css.msgserver.utils.TimeUtils;
import com.css.msgserver.ws.client.WSClientFactory;
@Transactional
@Service("openCardService")
public class OpenCardServiceImp implements IOpenCardService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FtpConnFactory ftpConnFactory;
	@Autowired
	private BkA0001DataDao bkA0001DataDao;
	@Autowired
	private BkA0001YwsjjDao bkA0001YwsjjDao;
	@Autowired
	private BkOpenCardDao bkOpenCardDao;
	@Autowired
	private BocDataPacketBuilder bocDataPacketBuilder;
	
	//
	@Value("${ftp.server.send.dir}")
	private String serverdir;
	//
	public BkA0001Data saveDfBkA0001Data() {
		//
		String packsize=BankConf.get("bank.boc.packsize");// 银行网点
		Integer fileSize=Integer.valueOf(packsize);//单包文件数据限制
		//
		List<BkA0001Data> yu=bkA0001DataDao.findBySendStateAndCzlxAndSenderAndWjsLessThan(SendState.DF, BkBocType.CZLX_FILE_SEND, BkBocType.JYDW_MY,fileSize);
		if(yu.size()>0){
			return yu.get(0);
		}
		//
		BkA0001Data bd = new BkA0001Data();
		bd.setCzlx(BkBocType.CZLX_FILE_SEND);
		bd.setCreateTime(new Date());
		bd.setWjs(0);
		bd.setSender(BkBocType.JYDW_MY);
		bd.setSendState(SendState.DF);
		bd.setUuid(IDUtil.getId());
		bkA0001DataDao.save(bd);
		return bd;
	}
	public BkA0001Ywsjj saveDfBkA0001Ywsjj(BkA0001Data bd,String zzlb) {
		BkA0001Ywsjj ywsjj=new BkA0001Ywsjj();
		ywsjj.setBkA0001DataUuid(bd.getUuid());
		ywsjj.setUuid(IDUtil.getId());
		ywsjj.setZklx(zzlb);
		//当前开卡批次
		Integer kkpc=bkA0001YwsjjDao.countByCreateTimeBetween(TimeUtils.get0Daystamp(),TimeUtils.get24Daystamp());
		if(kkpc==null)kkpc=0;
		ywsjj.setKkpc(kkpc+1);
		//
		bkA0001YwsjjDao.save(ywsjj);
		return ywsjj;
	}
	/**
	 * 锁定
	 * @param bks
	 */
	@Override
	public void lock(List<BkOpenCard> page){
		Iterator<BkOpenCard> bks=page.iterator();
		while(bks.hasNext()){
			BkOpenCard bk=bks.next();
			bk.setSendState(SendState.LC);
			bkOpenCardDao.save(bk);
		}
	}
	/**
	 * 解锁
	 * @param bks
	 */
	@Override
	public void relock(List<BkOpenCard> page,String sendState){
		Iterator<BkOpenCard> bks=page.iterator();
		while(bks.hasNext()){
			BkOpenCard bk=bks.next();
			bk.setSendState(sendState);
			bkOpenCardDao.save(bk);
		}
	}
	@Transactional(readOnly=true)
	@Override
	public List<BkOpenCard> getOpenCard(String zzlb,String sendState){
		Sort sort = new Sort(Direction.ASC, "createTime");
		// 每次生成2000个
		Pageable pageable = new PageRequest(0, 2000, sort);
		// 查询准备中的文件
		Page<BkOpenCard> ls = bkOpenCardDao.findBySendStateAndZzlb(sendState, zzlb,pageable);
		
		return ls.getContent();
	}
	@Transactional
	@Override
	public void produceOpenCardFile(List<BkOpenCard> page,String path) throws Exception{
			//
			String bankwd=BankConf.get("bank.boc.wd");// 银行网点
			//
			if(page.isEmpty())return;
			//
			String zzlb=page.get(0).getZzlb();
			//
			BkA0001Data bd = saveDfBkA0001Data();
			BkA0001Ywsjj jj=saveDfBkA0001Ywsjj(bd,zzlb);
			//创建数据文件
			String fname = IDUtil.getOpenCardFileName(zzlb, jj.getKkpc(), bankwd, page.size());
			String ljmPath=path + File.separator;
			String ljm=ljmPath + fname;
			BankTextWriter btw = new BankTextWriter(ljm);
			btw.open();
			//
			Iterator<BkOpenCard> bks=page.iterator();
			while(bks.hasNext()){
				BkOpenCard bk=bks.next();
				btw.write(BankBeanUtils.enPackData(bk));
				bk.setFileName(fname);
				bk.setSendState(SendState.DF);
				bkOpenCardDao.save(bk);
			}
			//
			btw.close();
			jj.setWjm(fname);
			jj.setLjm(serverdir);
			//如果开启文件加密
			FileUtils.enFile(new File(ljm));
			//
			jj.setJym(Md5Utils.getMd5byFile(ljm));
			//保存
			bd.setWjs(bd.getWjs()+1);
			bkA0001DataDao.save(bd);
			bkA0001YwsjjDao.save(jj);
	}
	@Override
	public void sendOpenCardDataValidate(BkA0001Data bd){
		List<BkA0001Ywsjj> bks = bkA0001YwsjjDao.findByBkA0001DataUuid(bd.getUuid());
		for(BkA0001Ywsjj yws:bks){
			if(yws.getFlag().equals(BkBocType.FLAG_ERROR)){
				
				int c=bkOpenCardDao.updateSendState(SendState.YF, yws.getWjm(), SendState.ZB);
				if(c>0){
					logger.info("开卡文件银行接收失败[错误号:"+yws.getSbyy()+",效验码："+yws.getJym()+"]系统自动重发:"+yws.getWjm());
				}else{
					logger.info("开卡文件银行接收失败[错误号:"+yws.getSbyy()+",效验码："+yws.getJym()+"]重发失败，未找到文件的开卡记录:"+yws.getWjm());
				}
			}else{
				//接收成功
				logger.info("开卡文件银行接收成功:"+yws.getWjm());
			}
		}
	}
	@Override
	public void sendOpenCardData(String directory,List<BkA0001Data> a0001Data) throws Exception{
		// 读取数据库
		//List<BkA0001Data> names = bkA0001DataDao.findBySendStateAndCzlxAndSender(SendState.DF,BkBocType.CZLX_FILE_SEND,BkBocType.JYDW_MY);
		ICallBocService ics = WSClientFactory.get(ICallBocService.class);
		//
		FTPClient fc = ftpConnFactory.getConnect();
		FtpService fs = new FtpService();
		fs.setClient(fc);
		// 效验服务器文件
		for (BkA0001Data n : a0001Data) {
			List<BkA0001Ywsjj> bks = bkA0001YwsjjDao.findByBkA0001DataUuid(n.getUuid());
			Set<String> files = new HashSet<String>();
			// 一个data文件清单
			for (BkA0001Ywsjj yws : bks) {
				files.add(yws.getWjm());
			}
			if (fs.exists(directory, files)) {
				//S001+密文。
				StringBuffer str = new StringBuffer();
				// ftp服务器上文件存在
				ListXSDataPacket pack = bocDataPacketBuilder.buildPacket(n, bks,PackType.in);
				// 打包数据
				str.append(XSDataPacketAdapter.toXmlStrBuf(pack));
				//加密并发送数据
				String bankre=ics.send(BankBeanUtils.enBocXml(str));
				String tdxy=BankBeanUtils.getBocTdxy(bankre);
				//通道效验码
				if(tdxy.equals(BkBocType.TDXY_OK)){
					//解密并保存数据
					logger.info("银行反回数据[bankre]>>>>"+bankre);
					SimpleXSDataPacket bankresult=XSDataPacketAdapter.toBean(BankBeanUtils.deBocXml(bankre,2), SimpleXSDataPacket.class);
					String re=bankresult.getDATASET().getDATA();
					//
					AnswerData ad=BankBeanUtils.dePackData(AnswerData.class, re);
					if(ad.getFhbz().equals(BkBocType.FHBZ_OK)){
						//成功
						logger.info(">>>>开卡银行收文件结果：成功!");
						//
						updateToYF(n,"成功");
						////修改状态
						for (BkA0001Ywsjj yws : bks) {
							//修改状态
							bkOpenCardDao.updateSendState(SendState.DF, yws.getWjm(), SendState.YF);
							//
						}
					}else{
						logger.info(">>>>开卡银行收文件结果：失败,下一次重发!");
						//失败
						updateToDF(n, "对方接收失败,下一次重发："+ad.getFhsbxx());
					}
				}else{
					//通道效验码错误
					updateToDF(n, "对方接收失败,下一次重发："+bankre);
				}
				str=null;//清空
				//
			} else {
				// 文件被删除需重传
				n.setSendState(SendState.DF);
				n.setRemark("FTP文件未上传,下一次重发");
				bkA0001DataDao.save(n);
			}
		}
	}
	private void updateToDF(BkA0001Data n, String remark) {
		updateSendStateAndRemark(n,SendState.DF,remark);
	}
	private void updateToYF(BkA0001Data n, String remark) {
		updateSendStateAndRemark(n,SendState.YF,remark);
	}
	private void updateSendStateAndRemark(BkA0001Data n,String sendState, String remark) {
		n.setSendState(sendState);
		//失败
		n.setRemark(remark);
		bkA0001DataDao.save(n);
	}
}
