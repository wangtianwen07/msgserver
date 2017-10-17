package com.css.msgserver.conf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.css.msgserver.utils.FileUtils;

@Configuration
public class BankConf {
	@Bean  
	public BankConf getBankConf(){
		return new BankConf();
	}
	private static Map<String,String> conf;
	public static String basePath="bank"+File.separator;
	public BankConf(){
		setConf();
	}
	public static void setConf(){
		Properties pro = new Properties();
		if(conf==null){
			try {
				InputStream is=FileUtils.getResourceAsStream(basePath+"bank_boc.properties");
				pro.load(is);
				conf=new HashMap<String,String>();
				for(String key:pro.stringPropertyNames()){
					conf.put(key, pro.getProperty(key));
				}
				is.close();
			} catch (IOException e) {
				throw new RuntimeException("bank_boc.properties未设置!");
			}
		}
		return;
	}
	public static String getRelativePath(String filename){
		return basePath+"tpl"+File.separator+filename;
	}
	public static InputStream getRealFile(String filename){
		return BankConf.class.getClassLoader().getResourceAsStream(getRelativePath(filename));
	}
	public static String get(String key){
		if(conf==null){
			setConf();
		}
		return conf.get(key);
	}
	public static Integer getInt(String key){
		String val=get(key);
		return Integer.valueOf(val);
	}
	public static Boolean getBoolean(String key){
		String val=get(key);
		return Boolean.valueOf(val);
	}
}
