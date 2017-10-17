package com.css.msgserver.csscis.hession.token.service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.util.StringUtils;  
public class Token {
		//私钥
		private String app_token="app_token-adfweobzvwrwlmd23432vlser5465243dfsfw";
		//时间戳时间设置不能超过某个时间，防止传输过程中signature窃取后的连续攻击，这个值也不能设置太小，因为可能存在网络缓慢或者不稳定
		private int time_out=3000;
		private String nonce;
		private String timestamp;
		private String sinatrue;
		public Token(){
			nonce=generateNonce();
			timestamp=generateTimestamp();
			sinatrue=generateSignature();
		}
		public Token(String timestamp,String nonce,String sinatrue){
			this.nonce=nonce;
			this.timestamp=timestamp;
			this.sinatrue=sinatrue;
		}
		private String generateNonce(){
			Random random = new Random(123456789);
			String nonce=random.nextInt()+""+random.nextInt()+""+random.nextInt()+""+random.nextInt();
			return nonce;
		}
		
		private String generateTimestamp(){
			return System.currentTimeMillis()+"";
		}
		
		/**
		 * 利用token，时间戳和随机数生成加密签名
		 * @return
		 */
		private String generateSignature(){
			return generateSignature(timestamp,nonce,app_token);
		}
		public String getNonce() {
			return nonce;
		}
		public void setNonce(String nonce) {
			this.nonce = nonce;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getSinatrue() {
			return sinatrue;
		}
		public void setSinatrue(String sinatrue) {
			this.sinatrue = sinatrue;
		}
		/**
		 * 利用token，时间戳和随机数生成加密签名
		 * @return
		 */
		public String generateSignature(String timestamp,String nonce,String app_token){
			String signature=sha1(app_token+timestamp+nonce);
			return signature;
		}
		public boolean validateSignature(){
			return validateSignature(sinatrue,timestamp,nonce);
		}
		/**
		 * 验证请求的签名
		 * @param signature
		 * @param timestamp
		 * @param nonce
		 * @return
		 */
		public boolean validateSignature(String signature,String timestamp,String nonce){
			if(StringUtils.isEmpty(signature) || 
					StringUtils.isEmpty(timestamp)||
					StringUtils.isEmpty(nonce)){
				return false;
			}
			long ctime=System.currentTimeMillis();
			long ttime=Long.valueOf(timestamp);
			if((ctime-ttime)<time_out){
				if(signature.equals(generateSignature(timestamp,nonce,app_token))){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
		/**
		 * sha1算法
		 * 
		 * @param text
		 * @return
		 */
		public String sha1(String text) {
			MessageDigest md = null;
			String outStr = null;
			try {
				md = MessageDigest.getInstance("SHA-1");
				byte[] digest = md.digest(text.getBytes());
				outStr = byteToString(digest);
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
			return outStr;
		}

		private String byteToString(byte[] digest) {
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < digest.length; i++) {
				String tempStr = Integer.toHexString(digest[i] & 0xff);
				if (tempStr.length() == 1) {
					buf.append("0").append(tempStr);
				} else {
					buf.append(tempStr);
				}
			}
			return buf.toString().toLowerCase();
		}
}
