package com.css.msgserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.css.msgserver.conf.BankConf;

public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	private static boolean deFile(File src,File out)  throws Exception{
		String key=BankConf.get("bank.boc.despwd");
		boolean sec=BankConf.getBoolean("bank.boc.sec");
		if(sec){
			DesUtil.decrypt(src, out, key);
		}else{
			return false;
		}
		return true;
	}
	public static boolean deFile(File src)  throws Exception{
		String srcname=src.getCanonicalPath();
		String tempname=getTempFileName(src);
		File srcfile=rename(src,tempname);
		File outfile=new File(srcname);
		boolean suc=deFile(srcfile,outfile);
		if(suc){
			srcfile.delete();
		}else{
			rename(srcfile,src.getName());
		}
		return true;
	}
	private static boolean enFile(File src,File out)  throws Exception{
		String key=BankConf.get("bank.boc.despwd");
		boolean sec=BankConf.getBoolean("bank.boc.sec");
		if(sec){
			DesUtil.encrypt(src, out, key);
		}else{
			logger.info("未开启加密!");
			return false;
		}
		return true;
	}
	public static boolean enFile(File src)  throws Exception{
		String srcname=src.getCanonicalPath();
		String tempname=getTempFileName(src);
		File srcfile=rename(src,tempname);
		File outfile=new File(srcname);
		boolean suc=enFile(srcfile,outfile);
		if(suc){
			srcfile.delete();
		}else{
			rename(srcfile,src.getName());
		}
		return true;
	}
	/**
	 * 同目录下得到一个临时文件
	 * @param s  文件
	 * @return
	 * @throws IOException
	 */
	public static String getTempFileName(File s) throws IOException{
		String name=s.getName();
		String ext=name.substring(name.lastIndexOf("."));
		String re=IDUtil.getId()+ext;
		return re;
	}
	/**
	 * 重命名
	 * @param src
	 * @param newname
	 * @return
	 * @throws IOException
	 */
	public static File rename(File src,String newname) throws IOException{
		String path=src.getParent();
		File newfile=new File(path+File.separator+newname); 
		if(newfile.exists()){
			logger.info(newname+"已经存在！"); 
			return null;
		}else{
			boolean sd=src.renameTo(newfile);
			//失败
			if(!sd){
				logger.info(src.getAbsolutePath()+"重命名["+newfile.getAbsolutePath()+"]失败！"); 
				return null;
			}
        } 
		return newfile;
	}
	/**
	 * 移动
	 * @param src
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static boolean move(File src,String path) throws IOException{
		String newname=src.getName();
		File newfile=new File(path+File.separator+newname); 
		if(newfile.exists()){
			logger.info(newname+"已经存在！"); 
			return false;
		}else{ 
           src.renameTo(newfile); 
       } 
		return true;
	}
	/**
	 * 
	 * 是否存在
	 * 
	 */
	public static boolean exists(String path,String name){
		File f=new File(path+File.separator+name);
		return f.exists();
	}
	public static boolean delete(String path,String name){
		File f=new File(path+File.separator+name);
		if(!f.exists())return true;
		return f.delete();
	}
	public static String getClassPath(String resourcesPath){
		return FileUtils.class.getClassLoader().getResource("").getPath()+resourcesPath;
	}
	public static InputStream getResourceAsStream(String resourcesPath) throws FileNotFoundException{
		String path=getClassPath(resourcesPath);
		logger.info("读取："+path); 
		File f=new File(path);
		FileInputStream fis=new FileInputStream(f);
		return fis;
	}
}
