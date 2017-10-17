package com.css.msgserver.bank.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.css.msgserver.bank.model.IYwsjj;
import com.css.msgserver.bank.service.IBankTextReader;

public class BankTextReader implements IBankTextReader,IYwsjj {
	private FileInputStream fis;
	private InputStreamReader isr;
	private BufferedReader br;
	private boolean nextable=true;
	public BankTextReader(String path){
		textFile=new File(path);
	}
	public BankTextReader(File path){
		textFile=path;
	}
	private File textFile;
	@Override
	public void setTextFile(String path) {
		textFile=new File(path);
	}

	@Override
	public File getTextFile() {
		
		return textFile;
	}
	@Override
	public void open() throws FileNotFoundException, UnsupportedEncodingException{
		fis=new FileInputStream(textFile);
        isr=new InputStreamReader(fis, "UTF-8");
		br=new BufferedReader(isr);
	}
	@Override
	public void close() throws IOException{
		br.close();
        isr.close();
        fis.close();
	}
	@Override
	public String readLine() throws IOException{
		return br.readLine();
	}
	@Override
	public String[] read() throws IOException{
			
		String line="";
        String[] arrs=null;
        line=readLine();
		if (line!=null) {
			 arrs=line.split("\\"+VALUE_STEP);
		}else{
			nextable=false;
		}
		return arrs;
	}
	@Override
	public boolean hasNext() {
		return nextable;
	}

}
