package com.css.msgserver.bank.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FtpService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private FTPClient client;
	
	public FTPClient getClient() {
		return client;
	}
	public void setClient(FTPClient client) {
		this.client = client;
	}
	/**
	 * 文件上传
	 * @param ftpClient
	 * @param srcFileName  需要上传的文件全路径：c:/addfk/adf.txt
	 * @param optfileName  目标存储文件名：adf.txt
	 * @param optDirectory 目标存储目录：/opt/local/file
	 * @return
	 * @throws Exception
	 */
	public boolean up(FTPClient ftpClient,String srcFileName,String optfileName,String optDirectory) throws Exception{
		File srcFile = new File(srcFileName);
		InputStream fis = new FileInputStream(srcFile);
		// 设置上传目录
		ftpClient.changeWorkingDirectory(optDirectory);
		ftpClient.setBufferSize(1024);
		ftpClient.setControlEncoding("gbk");
		// 设置文件类型（二进制）
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		boolean result = ftpClient.storeFile(optfileName, fis);
		IOUtils.closeQuietly(fis);
		return result;
	}
	public boolean up(String srcFileName,String optfileName,String optDirectory) throws Exception{
		return up(client,srcFileName, optfileName, optDirectory);
	}
	/**
	 * 文件下载
	 * @param ftpClient
	 * @param saveFilePath  保存本地路径
	 * @param destFileName  待下载的文件
	 * @param directory     工作目录
	 * @return
	 * @throws Exception
	 */
	public boolean down(FTPClient ftpClient,String saveFilePath,String destFileName,String directory) throws Exception{
		ftpClient.setBufferSize(1024);
		// 设置文件类型（二进制）
		ftpClient.changeWorkingDirectory(directory);
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		//本地保存
		String sp=saveFilePath+File.separator+destFileName;
		//
		FileOutputStream fos=new FileOutputStream(sp);
		boolean result = ftpClient.retrieveFile(destFileName, fos);
		fos.flush();
		fos.close();
		logger.info("download file "+directory+"/"+destFileName+" to local "+sp);
		return result;
	}
	public boolean down(String saveFilePath,String destFileName,String directory) throws Exception{
		return down(client,saveFilePath, destFileName, directory);
	}
	/**
	 * 查询目录
	 * @param ftpClient
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public String[] ls(FTPClient ftpClient,String directory) throws IOException{
		ftpClient.changeWorkingDirectory(directory);
		String[] list = ftpClient.listNames();//删除目录
		return list;
	}
	public String[] ls(String directory) throws IOException{
		return ls(client,directory);
	}
	/**
	 * 重命名文件、目录
	 * @param ftpClient
	 * @param directory
	 * @param oldFileName
	 * @param newfileName
	 * @return
	 * @throws IOException
	 */
	public boolean rename(FTPClient ftpClient,String directory,String oldFileName,String newfileName) throws IOException{
		ftpClient.changeWorkingDirectory(directory);
		boolean result = ftpClient.rename(oldFileName, newfileName);//重命名远程文件
		return result;
	}
	public boolean rename(String directory,String oldFileName,String newfileName) throws IOException{
		return rename(client,directory, oldFileName, newfileName);
	}
	/**
	 * 工作目录切换
	 * @param ftpClient
	 * @param directory
	 * @throws IOException
	 */
	public void cd(FTPClient ftpClient,String directory) throws IOException{
		ftpClient.changeWorkingDirectory(directory);
	}
	public void cd(String directory) throws IOException{
		client.changeWorkingDirectory(directory);
	}
	/**
	 * 文件删除
	 * @param ftpClient
	 * @param directory
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean remove(FTPClient ftpClient,String directory,String fileName) throws IOException{
		ftpClient.changeWorkingDirectory(directory);
		boolean result = ftpClient.deleteFile(fileName);//删除远程文件
		return result;
	}
	public boolean remove(String directory,String fileName) throws IOException{
		return remove(client,directory, fileName);
	}
	/**
	 * 文件是否存在
	 * @param ftpClient
	 * @param directory
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public boolean exists(FTPClient ftpClient,String directory,String fileName) throws IOException{
		String[] fles=ls(ftpClient,directory);
		for(String str:fles){
			if(str.equals(fileName)){
				return true;
			}
		}
		return false;
	}
	public Set<String> existsFile(FTPClient ftpClient,String directory,Set<String> fileNames) throws IOException{
		String[] fles=ls(ftpClient,directory);
		Set<String> exfiles=new HashSet<String>();
		for(String str:fles){
			if(fileNames.contains(str)){
				exfiles.add(str);
			}
		}
		return exfiles;
	}
	public boolean exists(FTPClient ftpClient,String directory,Set<String> fileNames) throws IOException{
		String[] fles=ls(ftpClient,directory);
		Set<String> exfiles=new HashSet<String>();
		for(String str:fles){
			if(fileNames.contains(str)){
				exfiles.add(str);
			}
		}
		return exfiles.size()==fileNames.size();
	}
	public boolean exists(String directory,String fileName) throws IOException{
		return exists(client,directory, fileName);
	}
	public boolean exists(String directory,Set<String> fileName) throws IOException{
		return exists(client,directory, fileName);
	}
	public Set<String> existsFile(String directory,Set<String> fileName) throws IOException{
		return existsFile(client,directory, fileName);
	}
	/**
	 * 目录删除
	 * @param ftpClient
	 * @param directory
	 * @param directoryName
	 * @return
	 * @throws IOException
	 */
	public boolean removeDirectory(FTPClient ftpClient,String directory,String directoryName) throws IOException{
		ftpClient.changeWorkingDirectory(directory);
		boolean result = ftpClient.removeDirectory(directoryName);//删除目录
		return result;
	}
	public boolean removeDirectory(String directory,String directoryName) throws IOException{
		return removeDirectory(client,directory, directoryName);
	}
	/**
	 * 创建目录
	 * @param ftpClient
	 * @param directory
	 * @param newDirectory
	 * @return
	 * @throws IOException
	 */
	public boolean mkdir(FTPClient ftpClient,String directory,String newDirectory) throws IOException{
		ftpClient.changeWorkingDirectory(directory);
		boolean result = ftpClient.makeDirectory(newDirectory);//创建新目录
		return result;
	}
	public boolean mkdir(String directory,String newDirectory) throws IOException{
		return mkdir(client,directory, newDirectory);
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
	public void disconnectFtp() throws IOException {
		disconnectFtp(client);
	}
}
