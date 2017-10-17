package com.css.msgserver.jpa.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BK_OPEN_CARD_RESULT")
public class BkOpenCardResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BK_OPEN_CARD_RESULT_UUID")
	private String uuid;
	
	@Pack(order=0)
	@Column(name = "SEQ_NO")
	private String seqNo;
	
	@Pack(order=1)
	@Column(name = "CARD_TYPE")
	private String cardType;
	
	@Pack(order=2)
	@Column(name = "CARD_ID")
	private String cardId;
	
	@Pack(order=3)
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Pack(order=4)
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Pack(order=5)
	@Column(name = "CUS_SEX")
	private String cusSex;
	
	@Pack(order=6)
	@Column(name = "CARD_NO")
	private String cardNo;
	
	@Pack(order=7)
	@Column(name = "ACT_TYPE")
	private String actType;
	
	@Pack(order=8)
	@Column(name = "RTN_CODE")
	private String rtnCode;
	
	@Pack(order=9)
	@Column(name = "RTN_MSG")
	private String rtnMsg;
	
	
	@Column(name = "SEND_STATE")
	private String sendState=SendState.DF;//非必填.
	
	@Column(name = "CREATE_TIME")
	private Date createTime=new Date();
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "REMARK")
	private String remark;
	
	
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

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCusSex() {
		return cusSex;
	}

	public void setCusSex(String cusSex) {
		this.cusSex = cusSex;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	
}
