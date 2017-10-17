package com.css.msgserver.bank.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * txt文本操作类
 * @author pc
 *
 */
public interface IBankTextReader {
	
	/**
	 * 设置文档
	 * @param path
	 */
	public void setTextFile(String path);
	
	/**
	 * 读取文档
	 * @return
	 */
	public File getTextFile();
	/**
	 * 读一行
	 * @return
	 */
	public String[] read() throws IOException;
	public String readLine() throws IOException;
	/**
	 * 是否还有下一行
	 * @return
	 */
	public boolean hasNext();

	void close() throws IOException;

	void open() throws FileNotFoundException, UnsupportedEncodingException;
}
