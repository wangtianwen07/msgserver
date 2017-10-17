package com.css.msgserver.utils;

import org.apache.commons.lang.StringUtils;

public class URLUtil {
	public static String getUrl(String url){
		if(StringUtils.isNotBlank(url)){
			if(url.indexOf("?")>-1){
				String[] urls=url.split("\\?");
				return urls[0];
			}else{
				return url;
			}
		}else{
			return "";
		}
	}
}
