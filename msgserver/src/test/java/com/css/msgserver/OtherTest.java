package com.css.msgserver;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;

import org.junit.Test;

import com.css.msgserver.bank.adapter.XSDataPacketAdapter;
import com.css.msgserver.bank.model.BkBocType;
import com.css.msgserver.bank.model.ListXSDataPacket;
import com.css.msgserver.jpa.pojo.BkA0001Data;
import com.css.msgserver.utils.BankBeanUtils;
import com.css.msgserver.utils.DesUtil;
import com.css.msgserver.utils.FileUtils;
import com.css.msgserver.utils.IDUtil;
import com.css.msgserver.utils.JsonUtil;
import com.css.msgserver.utils.Md5Utils;

public class OtherTest {
	@Test
	public void testIntStr() {
		System.out.println(IDUtil.getIntStr(4, 2000));
	}

	@Test
	public void testOpenCardFilename() {
		System.out.println(IDUtil.getOpenCardFileName(BkBocType.ZZLB_NEW_SHENZHEN_TONG, 21, "12456", 2000));
	}

	@Test
	public void testPackData() throws Exception {
		BkA0001Data bd = new BkA0001Data();

		bd.setCzlx("2");
		bd.setWjs(33);
		bd.setRemark("sdfsdf");
		System.out.println(BankBeanUtils.enPackData(bd));
	}
	@Test
	public void testPackDataList(){
		ListXSDataPacket dp=new ListXSDataPacket();
		
		dp.getWHERE().setJYLSH("3");
		dp.getDATASET().getYWSJJ().add("asdfa|asdfafs|adas");
		dp.getDATASET().getYWSJJ().add("32|2323|33");
		String str = XSDataPacketAdapter.toXmlStr(dp);
		System.out.println(str);
	}
	@Test
	public void testFileMd5() throws Exception{
		String sd=Md5Utils.getMd5byFile("D:\\csscis\\receive\\done\\20170928_00_1_00000001_17836_0004.txt");
		System.out.println("file md5:"+sd);
		sd=Md5Utils.getMd5byFile("D:\\csscis\\receive\\done\\error_2opencard_20170928_00_1_00000001_17836_0004.txt");
		System.out.println("file md5:"+sd);
		sd=Md5Utils.getMd5byFile("D:\\bank\\send\\done\\20170928_00_1_00000001_17836_0004.txt");
		System.out.println("file md5:"+sd);
	}
	@Test
	public void testStrBuff(){
		StringBuffer sb=new StringBuffer(BkBocType.JYDW_MY);
		sb.append("asdfas");
		sb.delete(BkBocType.JYDW_MY.length(), sb.length());
		System.out.println(sb.toString());
	}
	@Test
	public void testFileName(){
		System.out.println(IDUtil.getOpenCardFileNameByWjm("20170918_00_1_00000001_17836_0254.txt"));
	}
	@Test
	public void testDeStrBuff() throws Exception{
		StringBuffer sb=new StringBuffer();
		sb.append("44asdfas");
		String en=BankBeanUtils.enBocXml(sb);
		System.out.println("加密:"+en);
		String re=BankBeanUtils.deBocXml(en);
		System.out.println("解密:"+re.toString());
	}
	@Test
	public void testDePackData() throws Exception{
		BkA0001Data bd=BankBeanUtils.dePackData(BkA0001Data.class, "操作类型"+BankBeanUtils.VALUE_STEP+"2");
		System.out.println("-操作-:"+bd.getCzlx());
		System.out.println("---:"+bd.getWjs());
	}
	@Test
	public void testRenameFile() throws IOException{
		File s=new File("d:\\csscis\\receive\\temp\\20170928_00_1_00000001_17836_0004.txt");
		System.out.println(s.getParent());
		s=FileUtils.rename(s,"error_2opencard_20170928_00_1_00000001_17836_0004.txt");
		System.out.println(s.getAbsolutePath());
	}
	@Test
	public void testMove() throws IOException{
		File s=new File("f:/s.jpg");
		System.out.println(s.getParent());
		FileUtils.move(s,"F:\\music");
		System.out.println(s.getAbsolutePath());
	}
	@Test
	public void testSTRencode() throws Exception{
		String sd="121,-85,55,112,31,-5,-51,11";
		
		String en=DesUtil.encrypt("我的一家", sd);
		System.out.println(en);
		System.out.println(DesUtil.decrypt(en, sd));
	}
	@Test
	public void testFileencode() throws Exception{
		String sd="121,-85,55,112,31,-5,-51,11";
		
		DesUtil.encrypt(new File("D:\\bank\\send\\done\\20170928_00_1_00000001_17836_0004.txt"),new File("D:\\bank\\send\\done\\20170928_00_1_00000001_17836_0004_en.txt"), sd);
		DesUtil.decrypt(new File("D:\\bank\\send\\done\\20170928_00_1_00000001_17836_0004_en.txt"),new File("D:\\bank\\send\\done\\20170928_00_1_00000001_17836_0004_de.txt"), sd);
	}
	@Test
	public void testEnFile() throws Exception{
		File f=new File("D:\\bank\\send\\done\\20170928_01_0_00000001_17836_0002.txt");
		FileUtils.enFile(f);
	}
	@Test
	public void testDeFile() throws Exception{
		File f=new File("D:\\csscis\\receive\\done\\20170930_00_0_00000001_17836_0002.txt");
		FileUtils.deFile(f);
		f=new File("D:\\csscis\\receive\\done\\20170930_01_0_00000002_17836_0002.txt");
		FileUtils.deFile(f);
	}
	@Test
	public void testJSon(){
		System.out.println(JsonUtil.toSuccessStr("成功！"));
		String[] sd="662aa9d05d7c4f37af6c662fc89e9633|01|||刘谋成|1|60000124231310|1|0000|成功".split("\\|");
		for(String s:sd){
			System.out.println(s);
		}
	}
	@Test
	public void chToBig5() throws UnsupportedEncodingException{
		String sd=new String("國".getBytes("big5"),"gb2312");
		System.out.println(sd);
		sd=new String("國".getBytes("gb2312"),"big5");
		System.out.println(sd);
		sd=new String("國".getBytes("big5"),"utf-8");
		System.out.println(sd);
	}
}
