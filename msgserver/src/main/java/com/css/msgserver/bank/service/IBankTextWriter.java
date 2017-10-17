package com.css.msgserver.bank.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IBankTextWriter {
	/**
	 * 写一行
	 * @param re
	 * @return
	 */
	public boolean write(String[] re)throws IOException ;
	/**
	 * 写一行
	 * @param re
	 * @return
	 */
	public boolean write(StringBuffer re)throws IOException ;
	public boolean write(String re) throws IOException ;
	/**
	 *打开
	 * @param re
	 * @return
	 */
	void open() throws FileNotFoundException, UnsupportedEncodingException;
	/**
	 * 关闭
	 * @param re
	 * @return
	 */
	void close() throws IOException;
}
