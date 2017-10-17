package com.css.msgserver.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.css.msgserver.bank.service.impl.FtpException;
public class IDUtil {
	private static Integer mcount=1;
	private static Date sxDate=null;
	public static String getId(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 *设置失效时间
	 */
	private static void setSxDate(){
		Calendar cd=Calendar.getInstance();
		cd.add(Calendar.MINUTE,1);
		sxDate=cd.getTime();
	}
	/**
	 *1分钟以内的循环 
	 */
	private static Integer getMcount(){
		//未失效
		if(sxDate!=null && sxDate.after(new Date())){
			mcount++;
			return mcount;
		}else{
		//失效	
			setSxDate();
			mcount=1;
		}
		return mcount;
	}
	/**
	 * 中国银行交易流水号
	 * @return
	 */	
	public static String getBocJylsh(String serverid){
		String lsh=serverid+TimeUtils.getBkTimestamp()+getIntStr(4,getMcount());
		return lsh;
	}
	/**
	 * len长度，不够补0 如：0001
	 * @param len 长度
	 * @param val 值
	 * @return
	 */
	public static String getIntStr(int len,int val){
		return String.format("%0"+len+"d",val);
	}
	/**
	 * 
	 * 日期（yyyymmdd8位）+下划线+制证类别（2位）+下划线+标志位(1位)+下划线+开户批次号(8位)+下划线 +银行网点号（五位）+下划线+记录条数（4位，前补零）
	 * 
	 * @param zzlb  制证类别（2位）
	 * @param pc    开户批次号(8位)
	 * @param wd    银行网点号（五位）
	 * @param count 记录条数（4位，前补零）
	 * @return
	 */
	public static String getOpenCardFileName(String zzlb,int pc,String wd,int count){
		String lsh=TimeUtils.getBkDate()+"_"+zzlb+"_0_"+getIntStr(8,pc)+"_"+wd+"_"+getIntStr(4,count)+".txt";
		
		return lsh;
	}
	public static String getOpenCardFileNameByWjm(String filename){
		System.out.println(filename.length());
		String[] fls= filename.split("_");
		//fls[2]="0";
		if(fls.length!=6 || filename.length()!=37 || !fls[2].equals("1")){
			throw new FtpException("文件名校验不通过",FtpException.STATE_NAME_ERROR);
		}
		StringBuffer r=new StringBuffer(filename);
		r.replace(12, 13, "0");
		return r.toString();
	}
}
