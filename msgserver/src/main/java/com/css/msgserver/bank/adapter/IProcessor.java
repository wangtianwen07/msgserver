package com.css.msgserver.bank.adapter;

import java.io.File;

public interface IProcessor {
	public String[] doProcessor(String[] val,File file);
}
