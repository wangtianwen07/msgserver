package com.css.msgserver.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Utils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	public static String getMd5byFile(String path) throws Exception {
		File file = new File(path);
		return getMd5byFile(file);
	}

	private final static int handleBlockSize = 32 * 1024;

	/**
	 * 银行于09-28日提供MD5算方法
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getMd5byFile(File file) throws Exception {
		if (!file.exists()) {
			return null;
		}
		/*
		 * FileInputStream fis = new FileInputStream(file); MessageDigest md =
		 * MessageDigest.getInstance("MD5"); byte[] buffer = new byte[1024]; int
		 * length = -1; while ((length = fis.read(buffer, 0, 1024)) != -1) {
		 * md.update(buffer, 0, length); } BigInteger bigInt = new BigInteger(1,
		 * md.digest()); fis.close(); return bigInt.toString(16);
		 */
		BufferedInputStream bis = null;
		bis = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[handleBlockSize];
		MessageDigest alg = MessageDigest.getInstance("MD5");
		alg.reset();
		while (true) {
			int len = bis.read(buffer);
			if (len <= 0) {
				break;
			} else {
				alg.update(buffer, 0, len);
			}
		}
		bis.close();
		byte[] hash = alg.digest();
		String md5 = "";
		for (int i = 0; i < hash.length; i++) {
			int v = hash[i] & 0xFF;
			if (v < 16) {
				md5 += "0";
			}
			md5 += Integer.toString(v, 16).toUpperCase();
		}
		return md5;
	}

	public static boolean validateMd5byFile(String path, String md5) throws Exception {
		File file = new File(path);
		return validateMd5byFile(file, md5);
	}

	public static boolean validateMd5byFile(File file, String md5) throws Exception {
		String m = getMd5byFile(file);
		logger.info(m+":"+md5);
		if (m == null)
			return false;
		return m.equals(md5);
	}
}
