package com.css.msgserver.bank.adapter;

import java.io.File;

import org.springframework.stereotype.Component;

import com.css.msgserver.bank.service.impl.FtpException;
@Component("openCardResultValidateProcessor")
public class OpenCardResultValidateProcessor implements IProcessor {

	@Override
	public String[] doProcessor(String[] val,File file) {
		if(val.length<10){
			throw new FtpException("文件格式错误",FtpException.STATE_EXT_ERROR);
		}
		return val;
	}

}
