package com.css.msgserver.bank.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.css.msgserver.bank.adapter.BankTextProcessor;
import com.css.msgserver.bank.adapter.IProcessor;
import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.ftp.FtpConnFactory;
import com.css.msgserver.bank.ftp.FtpService;
import com.css.msgserver.bank.local.FileType;
import com.css.msgserver.bank.model.AnswerData;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.bank.model.SimpleXSDataPacket;
import com.css.msgserver.bank.service.IOpenCardResultService;
import com.css.msgserver.bus.bank.service.ICallBocService;
import com.css.msgserver.bus.csscis.service.ICallEbsOpenCardService;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkA0001Ywsjj;
import com.css.msgserver.jpa.pojo.BkOpenCardResult;
import com.css.msgserver.jpa.pojo.PackType;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.repository.BkA0001DataDao;
import com.css.msgserver.jpa.repository.BkA0001YwsjjDao;
import com.css.msgserver.jpa.repository.BkOpenCardResultDao;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.FileUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.JsonUtil;
import com.css.msgserver.utils.Md5Utils;
import com.css.msgserver.ws.client.WSClientFactory;

import net.sf.json.JSONObject;
@Transactional
@Service("openCardResultService")
public class OpenCardResultServiceImp implements IOpenCardResultService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FtpConnFactory ftpConnFactory;
	@Autowired
	private BkA0001YwsjjDao bkA0001YwsjjDao;
	@Autowired
	private BkA0001DataDao bkA0001DataDao;
	@Autowired
	private BkOpenCardResultDao bkOpenCardResultDao;
	@Autowired
	private IProcessor openCardResultValidateProcessor;
	@Autowired
	private IProcessor openCardResultProcessService;
	@Autowired
	private BocDataPacketBuilder bocDataPacketBuilder;
	//本地保存文件夹
	@Value("${ftp.local.receive.dir}")
	private String saveFilePath;
	
	
	/**
	 * 生成ftp服务器上下载文件的接收结果
	 * @param wjs
	 * @return
	 */
	public BkA0001Data saveDfBkA0001Data(Integer wjs) {
		BkA0001Data bd = new BkA0001Data();
		bd.setCzlx(BkBocType.CZLX_FILE_DOWN_VALIDATE);
		bd.setCreateTime(new Date());
		bd.setWjs(wjs);
		bd.setSender(BkBocType.JYDW_MY);
		bd.setSendState(SendState.DF);
		bd.setUuid(IDUtil.getId());
		bkA0001DataDao.save(bd);
		return bd;
	}
	/**
	 * 生成ftp服务器上下载文件的接收结果明细数据项
	 * @param bd
	 * @param bakjj
	 * @return
	 */
	public BkA0001Ywsjj saveDfBkA0001Ywsjj(BkA0001Data bd,BkA0001Ywsjj bakjj) {
		BkA0001Ywsjj ywsjj=new BkA0001Ywsjj();
		ywsjj.setBkA0001DataUuid(bd.getUuid());
		ywsjj.setUuid(IDUtil.getId());
		//当前开卡批次
		ywsjj.setKkpc(bakjj.getKkpc());
		//银行数据
		ywsjj.setWjm(bakjj.getWjm());
		ywsjj.setLjm(bakjj.getLjm());
		ywsjj.setZklx(bakjj.getZklx());
		ywsjj.setJym(bakjj.getJym());
		ywsjj.setYhbm(bakjj.getYhbm());
		//
		bkA0001YwsjjDao.save(ywsjj);
		return ywsjj;
	}
	@Override
	public void downOpenCardResult(BkA0001Data bkd) throws Exception{
		//得到待下载的数据
		List<BkA0001Ywsjj> fs=bkA0001YwsjjDao.findByBkA0001DataUuid(bkd.getUuid());
		FTPClient fc=ftpConnFactory.getConnect();
		FtpService ftp=new FtpService();
		ftp.setClient(fc);
		//返回给银行的数据
		BkA0001Data reBk001= saveDfBkA0001Data(bkd.getWjs());
		//
		for(BkA0001Ywsjj jj:fs){
			//
			BkA0001Ywsjj reBkYwsjj= saveDfBkA0001Ywsjj(reBk001,jj);
			//文件路径
			String newMd5="";
			File dfile=null;
			try{
				if(ftp.exists(jj.getLjm(),jj.getWjm())){
					//保存到临时文件夹
					String temp=saveFilePath+File.separator+"/temp";
					//存在则删除
					if(FileUtils.exists(temp, jj.getWjm())){
						if(!FileUtils.delete(temp, jj.getWjm())){
							throw  new RuntimeException("无法删除文件>>"+temp+File.separator+jj.getWjm());
						}
					}
					//下载文件
					boolean ok=ftp.down(temp, jj.getWjm(), jj.getLjm());
					if(!ok){
						throw new RuntimeException("无法下载文件>>"+jj.getLjm()+File.separator+jj.getWjm());
					}
					//
					dfile=new File(temp+File.separator+jj.getWjm());
					//校验文件名
					String filename=IDUtil.getOpenCardFileNameByWjm(jj.getWjm());
					int i=bkA0001YwsjjDao.countByWjm(filename);
					if(i<1){
						throw new FtpException("文件名校验不通过-未发送请求",FtpException.STATE_NAME_ERROR);
					}
					//校验文件
					if(!Md5Utils.validateMd5byFile(dfile, jj.getJym())){
						throw new FtpException("文件md5校验失败",FtpException.STATE_MD5_ERROR);
					}
					//文件解密
					if(!FileUtils.deFile(dfile)){
						throw new FtpException("文件解密失败",FtpException.STATE_DECODE_ERROR);
					}
					//文件格式验证
					BankTextProcessor bp=new BankTextProcessor(dfile,openCardResultValidateProcessor);
					bp.processor();
					//重命名成为规范的文件
					File tmpFile=FileUtils.rename(dfile, FileType.PREFIX_OPENCARD+dfile.getName());
					if(tmpFile==null){
						throw new RuntimeException("文件重命名错误"+dfile.getCanonicalPath());
					}
					dfile=tmpFile;
					//文件移动到正确目录
					boolean mv=FileUtils.move(dfile, saveFilePath);
					if(!mv){
						throw new RuntimeException("文件移动错误:"+dfile.getCanonicalPath());
					}
					//成功
					reBkYwsjj.setFlag(BkBocType.FLAG_OK);
					
				}else{
					throw new FtpException("文件不存在",FtpException.STATE_FILENOTFIND);
				}
			}catch(FtpException e){
				logger.info(e.getLocalizedMessage());
				reBkYwsjj.setSbyy(e.state);
				if(reBkYwsjj.getSbyy().equals(FtpException.STATE_MD5_ERROR)){
					reBkYwsjj.setFhjym(newMd5);
				}
				//创建返回报文
				reBkYwsjj.setFlag(BkBocType.FLAG_ERROR);
				//文件存在引起的错误
				if(!e.state.equals(FtpException.STATE_FILENOTFIND) && dfile!=null){
					//标识为错误文档
					dfile=FileUtils.rename(dfile, FileType.PREFIX_ERR+e.state+FileType.PREFIX_OPENCARD+dfile.getName());
					//文件移动到正确目录
					FileUtils.move(dfile, saveFilePath);
				}
			}
			//
			bkd.setSendState(SendState.ycl);
			//
			bkA0001DataDao.save(bkd);
			bkA0001YwsjjDao.save(reBkYwsjj);
		}
	}

	@Override
	public void saveOpenCardResult(File file) throws Exception{
		BankTextProcessor bp=new BankTextProcessor(file,openCardResultProcessService);
		bp.processor();
	}
	@Override
	public void sendBankDownResult(List<BkA0001Data> a0001Data) throws Exception{
		if(a0001Data==null || a0001Data.size()==0)return;
		ICallBocService ics = WSClientFactory.get(ICallBocService.class);
		//
		FTPClient fc = ftpConnFactory.getConnect();
		FtpService fs = new FtpService();
		fs.setClient(fc);
		// 效验服务器文件
		for (BkA0001Data n : a0001Data) {
			List<BkA0001Ywsjj> bks = bkA0001YwsjjDao.findByBkA0001DataUuid(n.getUuid());
			//S001+密文。
			StringBuffer str = new StringBuffer();
			// ftp服务器上文件存在
			ListXSDataPacket pack = bocDataPacketBuilder.buildPacket(n, bks,PackType.out);
			// 打包数据
			str.append(XSDataPacketAdapter.toXmlStrBuf(pack));
			//加密并发送数据
			String bankre=ics.send(BankBeanUtils.enBocXml(str));
			String tdxy=BankBeanUtils.getBocTdxy(bankre);
			//通道效验码
			if(tdxy.equals(BkBocType.TDXY_OK)){
				//解密并保存数据
				SimpleXSDataPacket bankresult=XSDataPacketAdapter.toBean(BankBeanUtils.deBocXml(bankre,2), SimpleXSDataPacket.class);
				String re=bankresult.getDATASET().getDATA();
				//
				AnswerData ad=BankBeanUtils.dePackData(AnswerData.class, re);
				if(ad.getFhbz().equals(BkBocType.FHBZ_OK)){
					//成功
					logger.info(">>>>文件档下载银行收文件结果："+bankre);
					//
					updateToYF(n,"成功");
				}else{
					updateToSB(n,"对方接收失败："+ad.getFhsbxx());
				}
			}else{
				updateToSB(n,"对方接收失败："+bankre);
			}
			str=null;//清空
				//
		}
	}
	@Override
	public void lock(List<BkOpenCardResult> bd) {
		updateSendState(bd,SendState.LC);
	}
	@Override
	public void unlock(List<BkOpenCardResult> bd,String sendState) {
		updateSendState(bd,sendState);
	}
	@Override
	public void unlock(BkOpenCardResult bd,String sendState) {
		updateSendState(bd,sendState);
	}
	@Transactional
	private void updateSendState(BkOpenCardResult bd,String sendState){
		bd.setSendState(sendState);
		bkOpenCardResultDao.save(bd);
	}
	private void updateSendState(List<BkOpenCardResult> bd,String sendState){
		for(BkOpenCardResult b:bd){
			updateSendState(b,sendState);
		}
	}
	@Transactional(readOnly=true)
	@Override
	public List<BkOpenCardResult> getOpenCardResult(String sendState,int size){
		Pageable pageable =new PageRequest(0, size);
		Page<BkOpenCardResult> p=bkOpenCardResultDao.findBySendState(SendState.DF, pageable);
		return p.getContent();
	}
	@Transactional(readOnly=true)
	@Override
	public int countOpenCardResult(String sendState){
		return bkOpenCardResultDao.countBySendState(sendState);
	}
	@Override
	public void sendOpenCardResult(List<BkOpenCardResult> openCardResult) throws Exception{
		logger.info("开卡结果发送给业务系统");
		if(openCardResult==null || openCardResult.size()==0)return;
		ICallEbsOpenCardService ics = WSClientFactory.get(ICallEbsOpenCardService.class);
		for(BkOpenCardResult o:openCardResult){
			String re=ics.openCardResult(JSONObject.fromObject(o).toString());
			JSONObject jo=JSONObject.fromObject(re);
			if(jo.getBoolean(JsonUtil.success)){
				o.setSendState(SendState.YF);
			}else{
				o.setSendState(SendState.SB);
				o.setRemark(jo.optString(JsonUtil.msg));
			}
			bkOpenCardResultDao.save(o);
		}
	}
	private void updateToSB(BkA0001Data n, String remark) {
		updateSendStateAndRemark(n,SendState.SB,remark);
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
