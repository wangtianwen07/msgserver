package com.css.msgserver.jpa.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BK_DICT_CONVERT")
public class BkDictConvert implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BK_DICT_CONVERT_UUID")
	private String uuid;
	@Column(name = "BK_NAME")
	private String bkName;
	@Column(name = "BK_VAL")
	private String bkVal;
	@Column(name = "SYS_NAME")
	private String sysName;
	@Column(name = "SYS_VAL")
	private String sysVal;
	@Column(name = "DICT_NAME")
	private String dictName;
	@Column(name = "REAMRK")
	private String reamrk;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBkName() {
		return bkName;
	}

	public void setBkName(String bkName) {
		this.bkName = bkName;
	}

	public String getBkVal() {
		return bkVal;
	}

	public void setBkVal(String bkVal) {
		this.bkVal = bkVal;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysVal() {
		return sysVal;
	}

	public void setSysVal(String sysVal) {
		this.sysVal = sysVal;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getReamrk() {
		return reamrk;
	}

	public void setReamrk(String reamrk) {
		this.reamrk = reamrk;
	}

}
