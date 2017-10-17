package com.css.msgserver.bank.adapter;

import java.io.File;
import java.io.IOException;

import com.css.msgserver.bank.service.IBankTextReader;
import com.css.msgserver.bank.service.IBankTextWriter;
import com.css.msgserver.bank.service.impl.BankTextReader;
import com.css.msgserver.bank.service.impl.BankTextWriter;
import com.css.msgserver.bank.service.impl.FtpException;

public class BankTextProcessor {
	private File in;
	private File out;
	private IProcessor processor;
	public BankTextProcessor(File in,File out,IProcessor processor){
		this.in=in;
		this.out=out;
		this.processor=processor;
	}
	public BankTextProcessor(String inpath,String outpath,IProcessor processor){
		this.in=new File(inpath);
		this.out=new File(outpath);
		this.processor=processor;
	}
	public BankTextProcessor(String inpath,IProcessor processor){
		this.in=new File(inpath);
		this.processor=processor;
	}
	public BankTextProcessor(File in,IProcessor processor){
		this.in=in;
		this.processor=processor;
	}
	public void processor() throws IOException{
		IBankTextReader read=new BankTextReader(in);
		read.open();
		IBankTextWriter writer=null;
		//无输出
		if(out!=null){
			writer=new BankTextWriter(out);
			writer.open();
		}
		try {
			while(read.hasNext()){
				String[] val=read.read();
				if(val!=null){
					String[] re=processor.doProcessor(val,in);
					//无输出
					if(out!=null){
						writer.write(re);
					}
				}
			}
		} catch (FtpException e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(read!=null){
				read.close();
			}
			//无输出
			if(out!=null){
				writer.close();
			}
		}
	}
	public File getResult(){
		return out;
	}
}
