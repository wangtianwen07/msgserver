package com.css.msgserver.bank.timer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.service.IOpenCardResultService;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkOpenCardResult;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.service.IBkA0001DataService;

/**
 * @author pc
 *接收银行数据
 */
@Component
public class ReceiveToBankScheduler {
	private boolean power=true;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IOpenCardResultService openCardResultService;
	@Autowired
	private IBkA0001DataService bkA0001DataService;
	
	
	@Value("${ftp.local.send.dir}")
	private String localSendDir;
	
	@Value("${ftp.server.send.dir}")
	private String serverSendDir;// 成功目录
	
	/**
	 * 从FTP服务器上下载开卡结果文档
	 * [下载并处理文件A]
	 */
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次  
	public void downOpenCardResultData(){
		logger.info("下载银行文档数据receiveOpenCardData");
		if(power){
			List<BkA0001Data> fs=bkA0001DataService.getUnDownloadData();
			//锁定用于分布式计算
			bkA0001DataService.lock(fs);
			for(BkA0001Data bd:fs){
				
				try {
					openCardResultService.downOpenCardResult(bd);
				}catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getLocalizedMessage());
					//解锁到待处理，下次再处理
					bkA0001DataService.unlock(bd, SendState.dcl);
				}
			}
		}
	}
	/**
	 * 通知银行已下载文档完成
	 * [调用银行接口，通知银行下载完成]
	 */
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次  
	public void sendBankDownResult(){
		logger.info("通知银行已下载文档完成sendBankDownResult");
		if(power){
			int page=10;
			int count=bkA0001DataService.countOpenCardData(SendState.DF,BkBocType.CZLX_FILE_DOWN_VALIDATE,BkBocType.JYDW_MY);
			int total=count/page+1;
			while(total>0 && count>0){
				List<BkA0001Data> bocs=bkA0001DataService.getOpenCardData(SendState.DF,BkBocType.CZLX_FILE_DOWN_VALIDATE,BkBocType.JYDW_MY, page);
				try {
					//锁定
					bkA0001DataService.lock(bocs);
					//
					openCardResultService.sendBankDownResult(bocs);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getLocalizedMessage());
					//锁定
					bkA0001DataService.unlock(bocs,SendState.DF);
				}
				total--;
			}
		}
	}
	/**
	 * 通知业务系统EBS开卡结果
	 */
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次  
	public void sendOpenCardResult(){
		logger.info("通知业务系统EBS开卡结果sendOpenCardResult");
		if(power){
			int page=100;
			int count=openCardResultService.countOpenCardResult(SendState.DF);
			int total=count/page+1;
			while(total>0){
				List<BkOpenCardResult> bocs=openCardResultService.getOpenCardResult(SendState.DF, page);
				try {
					//锁定
					openCardResultService.lock(bocs);
					//
					openCardResultService.sendOpenCardResult(bocs);
				}catch (Exception e) {
//					if(e instanceof ConnectException){
//						logger.error("ebs系统无法连接上");
//					}
					e.printStackTrace();
					logger.error(e.getLocalizedMessage());
					//锁定
					openCardResultService.unlock(bocs,SendState.DF);
				}
				total--;
			}
		}
	}
}
