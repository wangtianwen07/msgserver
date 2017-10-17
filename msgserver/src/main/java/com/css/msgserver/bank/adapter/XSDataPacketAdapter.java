package com.css.msgserver.bank.adapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import com.css.msgserver.bank.model.BaseXSDataPacket;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XSDataPacketAdapter {
	public static String xmlhead="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	
	public static final String code="utf-8";
	/**
	 * 初始化XStream
	 * @param clz
	 * @return
	 */
	private static XStream getXStream(Class clz){
		return getXStream(clz,code);
	}
	private static XStream getXStream(Class clz,String unicode){
		XStream xstream=new XStream(new DomDriver(unicode));
		xstream.processAnnotations(clz);
		return xstream;
	}
	public static <T> T toBean(String xml,Class<T> clz){
		XStream xstream=getXStream(clz);
		T obj=(T)xstream.fromXML(xml);
		return obj;
	}
	public static <T> T toBean(File xml,Class<T> clz){
		XStream xstream=getXStream(clz);;
		T obj=(T)xstream.fromXML(xml);
		return obj;
	}
	public static <T> T toBean(InputStream xml,Class<T> clz){
		XStream xstream=getXStream(clz);
		T obj=(T)xstream.fromXML(xml);
		return obj;
	}
	/**
	 * 得到基本数据类型
	 * @param xml
	 * @return
	 */
	public static BaseXSDataPacket toBean(File xml){
		XStream xstream=getXStream(BaseXSDataPacket.class);
		BaseXSDataPacket obj=(BaseXSDataPacket)xstream.fromXML(xml);
		return obj;
	}
	public static BaseXSDataPacket toBean(String xml){
		XStream xstream=getXStream(BaseXSDataPacket.class);
		BaseXSDataPacket obj=(BaseXSDataPacket)xstream.fromXML(xml);
		return obj;
	}
	public static String toXmlStr(Object obj){
		return toXmlStrBuf(obj).toString();
	}
	public static StringBuffer toXmlStrBuf(Object obj){
		XStream xstream=getXStream(obj.getClass()); //指定编码解析器,直接用jax3
		StringBuffer sb=new StringBuffer(xmlhead);
		sb.append(xstream.toXML(obj));
		return sb;
	}
	public static void toXmlStrBuf(Object obj,Writer os)throws IOException{
		XStream xstream=getXStream(obj.getClass());
		os.write(xmlhead);
		xstream.toXML(obj,os);
		return;
	}
}
