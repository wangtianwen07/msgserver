package com.css.msgserver.jpa.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BK_A0001_YWSJJ")
public class BkA0001Ywsjj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BK_A0001_YWSJJ_UUID")
	private String uuid = "";
	@Column(name = "BK_A0001_DATA_UUID")
	private String bkA0001DataUuid = "";
	
	
	@Pack(order=0)
	@Column(name = "WJM")
	private String wjm = "";
	
	@Pack(order=1)
	@Column(name = "LJM")
	private String ljm = "";
	
	@Pack(order=2)
	@Column(name = "ZKLX")
	private String zklx = "";
	
	@Pack(order=3)
	@Column(name = "YHBM")
	private String yhbm = "";
	
	@Pack(order=4)
	@Column(name = "JYM")
	private String jym = "";
	
	@Pack(order=5,type=PackType.out)
	@Column(name = "FLAG")
	private String flag = "";
	
	@Pack(order=6,type=PackType.out)
	@Column(name = "SBYY")
	private String sbyy = "";
	
	@Pack(order=7,type=PackType.out)
	@Column(name = "FHJYM")
	private String fhjym = "";
	
	@Column(name = "CREATE_TIME")
	private Date createTime=new Date();
	@Column(name = "KKPC")
	private Integer kkpc;
	public String getWjm() {
		return wjm;
	}

	public void setWjm(String wjm) {
		this.wjm = wjm;
	}

	public String getLjm() {
		return ljm;
	}

	public void setLjm(String ljm) {
		this.ljm = ljm;
	}

	

	public String getZklx() {
		return zklx;
	}

	public void setZklx(String zklx) {
		this.zklx = zklx;
	}

	public String getYhbm() {
		return yhbm;
	}

	public void setYhbm(String yhbm) {
		this.yhbm = yhbm;
	}

	public String getJym() {
		return jym;
	}

	public void setJym(String jym) {
		this.jym = jym;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getSbyy() {
		return sbyy;
	}

	public void setSbyy(String sbyy) {
		this.sbyy = sbyy;
	}

	public String getFhjym() {
		return fhjym;
	}

	public void setFhjym(String fhjym) {
		this.fhjym = fhjym;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBkA0001DataUuid() {
		return bkA0001DataUuid;
	}

	public void setBkA0001DataUuid(String bkA0001DataUuid) {
		this.bkA0001DataUuid = bkA0001DataUuid;
	}

	public Integer getKkpc() {
		return kkpc;
	}

	public void setKkpc(Integer kkpc) {
		this.kkpc = kkpc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

//	@Override
//	public String toPackData() {
//		
//		return wjm+"|"+ljm+"|"+zklx+"|"+yhbm+"|"+jym+"|"+flag+"|"+sbyy+"|"+fhjym;
//	}

}
