package com.css.msgserver.bank.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.css.msgserver.bank.model.IYwsjj;
import com.css.msgserver.bank.service.IBankTextWriter;

public class BankTextWriter implements IBankTextWriter ,IYwsjj {
	private FileOutputStream fis;
	private OutputStreamWriter isr;
	private BufferedWriter br;
	private File textFile;
	public BankTextWriter(File f){
		textFile=f;
	}
	public BankTextWriter(String path){
		textFile=new File(path);
	}
	@Override
	public void open() throws FileNotFoundException, UnsupportedEncodingException{
		fis=new FileOutputStream(textFile);
        isr=new OutputStreamWriter(fis, "UTF-8");
		br=new BufferedWriter(isr);
	}
	@Override
	public boolean write(String[] re) throws IOException {
		StringBuffer result=new StringBuffer();
		for(String f:re){
			result.append(f).append(VALUE_STEP);
		}
		result.append(LINE_STEP);
		br.write(result.toString());
		return true;
	}
	@Override
	public void close() throws IOException{
		br.close();
        isr.close();
        fis.close();
	}
	@Override
	public boolean write(StringBuffer re) throws IOException {
		re.append(LINE_STEP);
		br.write(re.toString());
		return true;
	}
	@Override
	public boolean write(String re) throws IOException {
		StringBuffer sb=new StringBuffer(re);
		sb.append(LINE_STEP);
		br.write(sb.toString());
		return true;
	}
}
