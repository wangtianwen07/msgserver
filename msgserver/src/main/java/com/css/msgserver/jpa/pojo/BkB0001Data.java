package com.css.msgserver.jpa.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BK_B0001_DATA")
public class BkB0001Data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BK_B0001_DATA_UUID")
	private String uuid;

	@Pack(order=0)
	@Column(name = "YHDM")
	private String yhdm;
	
	@Pack(order=1)
	@Column(name = "ZJHM")
	private String zjhm;
	
	@Pack(order=2)
	@Column(name = "ZJLX")
	private String zjlx;
	
	@Pack(order=3)
	@Column(name = "GHBH")
	private String ghbh;

	@Pack(order=4)
	@Column(name = "GLFS")
	private String glfs;

	@Pack(order=5)
	@Column(name = "CZLX")
	private String czlx;
	
	@Column(name = "CREATE_TIME")
	private Date createTime=new Date();
	@Column(name = "SEND_STATE")
	private String sendState=SendState.DF;//非必填
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getYhdm() {
		return yhdm;
	}

	public void setYhdm(String yhdm) {
		this.yhdm = yhdm;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getGhbh() {
		return ghbh;
	}

	public void setGhbh(String ghbh) {
		this.ghbh = ghbh;
	}

	public String getGlfs() {
		return glfs;
	}

	public void setGlfs(String glfs) {
		this.glfs = glfs;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

}
