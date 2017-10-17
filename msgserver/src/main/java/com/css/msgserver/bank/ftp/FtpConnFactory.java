package com.css.msgserver.bank.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class FtpConnFactory {
	@Value("${ftp.server.ip}")
	private String url;
	@Value("${ftp.server.port}")
	private Integer port;
	@Value("${ftp.server.username}")
	private String username;
	@Value("${ftp.server.password}")
	private String password;
	//
	public FTPClient getConnect()throws IOException {
		return connectFTP(url,port,username,password);
	}
	public FTPClient connectFTP(String url, int port, String username, String password) throws IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(url, port);
			ftpClient.login(username, password);
			ftpClient.enterLocalPassiveMode();
			return ftpClient;
		} catch (IOException e) {
			System.out.println("FTP端口配置错误:不是数字:"+url+":"+port+"?username="+username+"&password="+password);
			throw e;
		}
	}

	public void disconnectFtp(FTPClient mFTPClient) throws IOException {
		if (mFTPClient == null) {
			return;
		}
		if (!mFTPClient.isConnected()) {
			return;
		}
		mFTPClient.disconnect();
	}
}