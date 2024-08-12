package com.integrosys.cms.app.sharesecurity.bus;

import java.io.Serializable;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class OBShareSecurityValidation implements Serializable {

	private long securityId = ICMSConstant.LONG_INVALID_VALUE;

	private String securityType;

	private String securitySubType;

	private String country;

	private String state;

	private String district;

	private String mukim;

	private String titleNumber;

	private String titleType;

	private String providerName;

	private String idNumber;

	private String idCountry;

	public long getSecurityId() {
		return securityId;
	}

	public void setSecurityId(long securityId) {
		this.securityId = securityId;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getMukim() {
		return mukim;
	}

	public void setMukim(String mukim) {
		this.mukim = mukim;
	}

	public String getTitleNumber() {
		return titleNumber;
	}

	public void setTitleNumber(String titleNumber) {
		this.titleNumber = titleNumber;
	}

	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}

}
