package com.css.msgserver.bank.timer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.service.IOpenCardService;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.jpa.pojo.BkOpenCard;
import com.css.msgserver.jpa.pojo.SendState;
import com.css.msgserver.jpa.service.IBkA0001DataService;

/**
 * @author pc
 *发送银行数据
 */
@Component
public class SendToBankScheduler {
	private boolean power=true;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private IOpenCardService openCardService;
	@Autowired
	private IBkA0001DataService bkA0001DataService;
	@Value("${ftp.local.send.dir}")
	private String localSendDir;
	
	@Value("${ftp.server.send.dir}")
	private String serverSendDir;// 成功目录
	/**
	 * 
	 *发送银行开卡数据 
	 * [调用银行KACALLYH02接口，通知银行]
	 */
	@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次  
	public void sendOpenCardData(){
		logger.info("发送银行数据sendOpenCardData");
		//发送银行
		if(power){
			int page=10;
			int count=bkA0001DataService.countOpenCardData(SendState.DF,BkBocType.CZLX_FILE_SEND,BkBocType.JYDW_MY);
			int total=count/page+1;
			logger.info("发送银行数据sendOpenCardData-total:"+total);
			while(total>0 && count>0){
				List<BkA0001Data> bocs=bkA0001DataService.getOpenCardData(SendState.DF,BkBocType.CZLX_FILE_SEND,BkBocType.JYDW_MY, page);
				logger.info("发送银行数据sendOpenCardData:"+total+"-"+bocs.size());
				try {
					//锁定
					bkA0001DataService.lock(bocs);
					//
					openCardService.sendOpenCardData(serverSendDir,bocs);
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
	 * 
	 * 生产文档，生成开卡数据文件  
	 * [生成银行开户文件A,上送ftp服务器]
	 */
	@Scheduled(cron="0 0/1 * * * ?") 
	public void produceOpenCardData(){
		logger.info("生产文档  数据produceOpenCardData");
		//生成文件
		if(power){
			//有深圳通
			List<BkOpenCard> bocs=openCardService.getOpenCard(BkBocType.ZZLB_NEW_SHENZHEN_TONG,SendState.ZB);
			try {
				//锁定
				openCardService.lock(bocs);
				//
				openCardService.produceOpenCardFile(bocs,localSendDir);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getLocalizedMessage());
				//解锁
				openCardService.relock(bocs, SendState.ZB);
			}
			//无深圳通
			bocs=openCardService.getOpenCard(BkBocType.ZZLB_NEW_UN_SHENZHEN_TONG,SendState.ZB);
			try {
				//锁定
				openCardService.lock(bocs);
				//解锁
				openCardService.produceOpenCardFile(bocs,localSendDir);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getLocalizedMessage());
				openCardService.relock(bocs, SendState.ZB);
			}
		}
	}
	/**
	 * 
	 *发送银行文件结果处理 
	 * 【
	 * 银行下载文件异常处理
		文件A不存在
		文件A校验不通过
		文件A格式不符
	 * 】
	 */
	@Scheduled(cron="0 0/1 * * * ?") 
	public void sendOpenCardDataValidate(){
		logger.info("发送银行文件结果处理sendOpenCardDataValidate ");
		if(power){
			List<BkA0001Data> fs=bkA0001DataService.getSendOpenCardDataValidate();
			//锁定用于分布式计算
			bkA0001DataService.lock(fs);
			for(BkA0001Data bd:fs){
				try {
					openCardService.sendOpenCardDataValidate(bd);
				}catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getLocalizedMessage());
					//解锁到待处理，下次再处理
					bkA0001DataService.unlock(bd, SendState.dcl);
				}
			}
		}
	}
}
