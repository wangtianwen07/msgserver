package com.css.msgserver.ws.client;

import java.util.List;
import com.css.msgserver.ws.utils.WsAxis2User;

public class ParseKeyword {
	private static final String reg = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
	private static RegExp re = new RegExp();

	public static List<String> getKeywords(String p) {
		List<String> list = re.find(reg, p);
		return list;
	}
	public static String getSysConf(String key) {
		WsAxis2User u=new WsAxis2User();
		return u.get(key);
	}

	public static String parseValue(String str, String key, String val) {
		str = str.replace("${" + key + "}", val);
		return str;
	}

	public static String parseValue(String paramUrl) {
		List<String> keys = getKeywords(paramUrl);
		for (String key : keys) {
			String val = getSysConf(key);
			paramUrl = parseValue(paramUrl, key, val);
		}
		return paramUrl;

	}

	public static void main(String[] args) {
		System.out.println(ParseKeyword.parseValue("${app.ws.bank.url}/asdlfelks"));
	}
}
