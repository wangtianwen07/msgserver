package com.css.msgserver.jpa.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.css.msgserver.bank.model.BkBocType;

@Entity
@Table(name = "BK_OPEN_CARD")
public class BkOpenCard implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BK_OPEN_CARD_UUID")
	private String uuid;
	
	@Pack(order=0)
	@Column(name = "SEQ_NO")
	private String seqNo;//必填
	
	@Pack(order=1)
	@Column(name = "OPER_TYPE")
	private String operType;//必填
	
	@Pack(order=2)
	@Column(name = "CARD_TYPE")
	private String cardType;//必填
	
	@Pack(order=3)
	@Column(name = "CARD_ID")
	private String cardId;//必填
	
	@Pack(order=4)
	@Column(name = "BIRTHDAY")
	private String birthday;//必填
	
	@Pack(order=5)
	@Column(name = "CARD_EXPIRE_DATE")
	private String cardExpireDate;//固定
	
	@Pack(order=6)
	@Column(name = "FIRST_NAME")
	private String firstName;//必填
	
	@Pack(order=7)
	@Column(name = "LAST_NAME")
	private String lastName;//必填
	
	@Pack(order=8)
	@Column(name = "CUS_SEX")
	private String cusSex;//必填
	
	@Pack(order=9)
	@Column(name = "CUS_ADDR")
	private String cusAddr;//非必填
	
	@Pack(order=10)
	@Column(name = "COMPANY_NAME")
	private String companyName;//必填
	
	@Pack(order=11)
	@Column(name = "COMPANY_CODE")
	private String companyCode;//非必填
	
	@Pack(order=12)
	@Column(name = "COMPANY_OPER")
	private String companyOper;//非必填
	
	@Pack(order=13)
	@Column(name = "CERT_ADDR")
	private String certAddr;//必填，当证件类型为身份证、临时身份证、户口簿时必须填写
	
	@Pack(order=14)
	@Column(name = "CUS_COUNTRY")
	private String cusCountry;//必填
	
	@Pack(order=15)
	@Column(name = "SEC_COUNTRY")
	private String secCountry;//选填
	
	@Pack(order=16)
	@Column(name = "CUS_NATIONNALITY")
	private String cusNationnality;//必填，当证件类型为身份证、临时身份证、户口簿时必须填写
	
	@Pack(order=17)
	@Column(name = "VISA_TYPE")
	private String visaType;//必填，证件类型为03-时，必须填写签证类型
	
	@Pack(order=18)
	@Column(name = "VISA_EXPIRE_DATE")
	private String visaExpireDate;//签证类型不为空且值不是VF-免答时必填（格式 YYYYMMDD）
	
	@Pack(order=19)
	@Column(name = "CUS_TEL")
	private String cusTel;//必填，客户电话，填数字
	
	@Pack(order=20)
	@Column(name = "COMPANY_TRADE")
	private String companyTrade;//必填，
	
	@Pack(order=21)
	@Column(name = "WORK_TYPE")
	private String workType;//必填，
	
	@Pack(order=22)
	@Column(name = "IN_COME_LEVEL")
	private String inComeLevel;//必填，
	
	@Pack(order=23)
	@Column(name = "TRADE_UNION_CARD_NO")
	private String tradeUnionCardNo;//必填，
	
	@Pack(order=24)
	@Column(name = "OPER_ID")
	private String operId;//非必填
	
	@Pack(order=25)
	@Column(name = "MARK_OPER")
	private String markOper;//非必填
	
	@Pack(order=26)
	@Column(name = "APPLY_DATE")
	private String applyDate;//必填
	
	@Pack(order=27)
	@Column(name = "STDR1")
	private String stdr1;//非必填
	//
	@Column(name = "CREATE_TIME")
	private Date createTime=new Date();//非必填
	
	
	@Column(name = "SEND_STATE")
	private String sendState=SendState.DF;//非必填.
	
	
	@Column(name = "ZZLB")
	private String zzlb=BkBocType.ZZLB_NEW_UN_SHENZHEN_TONG;//非必填
	//
	@Column(name = "FILE_NAME")
	private String fileName;
	//
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

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCardExpireDate() {
		return cardExpireDate;
	}

	public void setCardExpireDate(String cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
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

	public String getCusAddr() {
		return cusAddr;
	}

	public void setCusAddr(String cusAddr) {
		this.cusAddr = cusAddr;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyOper() {
		return companyOper;
	}

	public void setCompanyOper(String companyOper) {
		this.companyOper = companyOper;
	}

	public String getCertAddr() {
		return certAddr;
	}

	public void setCertAddr(String certAddr) {
		this.certAddr = certAddr;
	}

	public String getCusCountry() {
		return cusCountry;
	}

	public void setCusCountry(String cusCountry) {
		this.cusCountry = cusCountry;
	}

	public String getSecCountry() {
		return secCountry;
	}

	public void setSecCountry(String secCountry) {
		this.secCountry = secCountry;
	}


	

	public String getVisaExpireDate() {
		return visaExpireDate;
	}

	public void setVisaExpireDate(String visaExpireDate) {
		this.visaExpireDate = visaExpireDate;
	}

	public String getCusTel() {
		return cusTel;
	}

	public void setCusTel(String cusTel) {
		this.cusTel = cusTel;
	}

	public String getCompanyTrade() {
		return companyTrade;
	}

	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}


	public String getCusNationnality() {
		return cusNationnality;
	}

	public void setCusNationnality(String cusNationnality) {
		this.cusNationnality = cusNationnality;
	}

	public String getInComeLevel() {
		return inComeLevel;
	}

	public void setInComeLevel(String inComeLevel) {
		this.inComeLevel = inComeLevel;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getMarkOper() {
		return markOper;
	}

	public void setMarkOper(String markOper) {
		this.markOper = markOper;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getStdr1() {
		return stdr1;
	}

	public void setStdr1(String stdr1) {
		this.stdr1 = stdr1;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSendState() {
		return sendState;
	}

	public void setSendState(String sendState) {
		this.sendState = sendState;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getTradeUnionCardNo() {
		return tradeUnionCardNo;
	}

	public void setTradeUnionCardNo(String tradeUnionCardNo) {
		this.tradeUnionCardNo = tradeUnionCardNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getZzlb() {
		return zzlb;
	}

	public void setZzlb(String zzlb) {
		this.zzlb = zzlb;
	}
}
