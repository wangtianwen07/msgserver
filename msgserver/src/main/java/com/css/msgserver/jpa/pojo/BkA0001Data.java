package com.css.msgserver.jpa.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.css.msgserver.bank.model.BkBocType;

@Entity
@Table(name = "BK_A0001_DATA")
public class BkA0001Data implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BK_A0001_DATA_UUID")
	private String uuid;
	
	@Pack(order=0)
	@Column(name = "CZLX")
	private String czlx=BkBocType.CZLX_FILE_SEND;
	
	
	@Pack(order=1)
	@Column(name = "WJS")
	private Integer wjs;
	
	@Column(name = "SEND_STATE")
	private String sendState=SendState.DF;//非必填
	
	@Column(name = "CREATE_TIME")
	private Date createTime=new Date();
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "SENDER")
	private String sender;
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getWjs() {
		return wjs;
	}

	public void setWjs(Integer wjs) {
		this.wjs = wjs;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
}
