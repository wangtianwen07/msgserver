package com.css.msgserver.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 银行于09-28日提供DES算方法
 * DES加密解密组件
 */
/**
 * DES加密解密组件
 */
public class DesUtil
{

	/**
	 * DES工作模式,有很多种,这里只写出最常用的2种
	 */
	public static enum DesMode
	{

		ECB_NOPADDING("DES/ECB/NoPadding"),

		ECB_PKCS5Padding("DES/ECB/PKCS5Padding");

		private final String mode;

		DesMode(String mode)
		{
			this.mode = mode;
		}

		public String getMode()
		{
			return mode;
		}

	}

	/**
	 * 将二进制的字节密钥转为对象
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Key toKey(byte[] key) throws DecryptException
	{

		SecretKey sk;
		try {
			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

			sk = keyFactory.generateSecret(dks);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DecryptException("密钥转换失败！",e);
		}
		return null;
	}
	/**
	 * 
	 * "121,-85,55,112,31,-5,-51,11"
	 */
	public static Key toKey(String s) throws DecryptException{
		String[] sd=s.split(",");
		int[] ints=new int[sd.length];
		for(int i=0;i<sd.length;i++){
			ints[i]=Integer.valueOf(sd[i]);
		}
		byte[] bys=new byte[ints.length];
		for(int i=0;i<ints.length;i++){
			bys[i]=(byte)ints[i];
		}
		return toKey(bys);
	}
	// 解密
	public static byte[] decrypt(byte[] data, Key k, DesMode mode) throws DecryptException
	{
		if (mode == null)
		{
			throw new NullPointerException("mode 不能为空");
		}
		try {
			Cipher cipher = Cipher.getInstance(mode.getMode());
			cipher.init(Cipher.DECRYPT_MODE, k);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DecryptException("解密失败！",e);
		}
	}

	// 加密
	public static byte[] encrypt(byte[] data, Key k, DesMode mode) throws DecryptException
	{
		if (mode == null)
		{
			throw new NullPointerException("mode 不能为空");
		}
		try {
			Cipher cipher = Cipher.getInstance(mode.getMode());
			cipher.init(Cipher.ENCRYPT_MODE, k);
			return cipher.doFinal(data);
		}  catch (Exception e) {
			e.printStackTrace();
			throw new DecryptException("加密失败！",e);
		}
	}
	public static void encrypt(File src, File dest, byte[] key) throws DecryptException{
		encrypt(src,dest,toKey(key));
	}
	public static void encrypt(File src, File dest, String key) throws DecryptException{
		encrypt(src,dest,toKey(key));
	}
	// 加密
	public static void encrypt(File src, File dest,Key k) throws DecryptException
	{
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, k);

			FileInputStream fis = new FileInputStream(src);
			CipherOutputStream cos = new CipherOutputStream(new FileOutputStream(dest), cipher);

			byte[] buffer = new byte[1024];
			int length = -1;

			while ((length = fis.read(buffer)) != -1)
			{
				cos.write(buffer, 0, length);
			}

			cos.flush();

			cos.close();
			fis.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new DecryptException("加密错误！",e);
		} 
	}
	public static void decrypt(File src, File dest, byte[] key) throws DecryptException{
		decrypt(src,dest,toKey(key));
	}
	public static void decrypt(File src, File dest, String key) throws DecryptException{
		decrypt(src,dest,toKey(key));
	}
	// 解密
	public static void decrypt(File src, File dest, Key k) throws DecryptException
	{
		try {
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, k);

			FileOutputStream fos = new FileOutputStream(dest);
			CipherInputStream cis = new CipherInputStream(new FileInputStream(src), cipher);

			byte[] buffer = new byte[1024];
			int length = -1;

			while ((length = cis.read(buffer)) != -1)
			{
				fos.write(buffer, 0, length);
			}

			fos.flush();

			fos.close();
			cis.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DecryptException("解密错误！",e);
		} 
	}

	public static String encrypt(String src, String charsetName, Key key)
	{
		try
		{
			byte[] bt = encrypt(src.getBytes(charsetName), key, DesMode.ECB_PKCS5Padding);
			Base64 decoder = new Base64();
			return decoder.encodeAsString(bt);
		}
		catch (Exception e)
		{
			throw new DecryptException("加密异常", e);
		}
		
		
	}
	public static String encrypt(String src, Key key)
	{
		return encrypt(src,"utf-8",key);
	}
	public static String encrypt(String src, String key) throws DecryptException
	{
		return encrypt(src,toKey(key));
	}
	public static String decrypt(String src, Key key){
		return decrypt(src,"utf-8",key);
	}
	public static String decrypt(String src, String key) throws DecryptException{
		return decrypt(src,toKey(key));
	}
	public static String decrypt(String src, String charsetName, Key key)
	{
		try
		{
			Base64 decoder = new Base64();
			byte[] buf = decoder.decode(src);
			byte[] bt = decrypt(buf, key, DesMode.ECB_PKCS5Padding);
			return new String(bt,charsetName);
		}
		catch (Exception e)
		{
			throw new DecryptException("解密异常", e);
		}

		
	}
	public static byte[] generateRandomKey()
	{

		KeyGenerator kg = null;
		try
		{
			kg = KeyGenerator.getInstance("DES");
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new DecryptException("JDK不支持此算法[DES]", e);
		}

		kg.init(56);

		SecretKey secretKey = kg.generateKey();

		return secretKey.getEncoded();
	}

}