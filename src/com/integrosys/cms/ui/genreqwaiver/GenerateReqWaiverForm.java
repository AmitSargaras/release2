/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genreqwaiver;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/15 12:04:52 $ Tag: $Name: $
 */

public class GenerateReqWaiverForm extends TrxContextForm implements Serializable {

	private String waiverReason = "";

	private String propName = "";

	private String propSignNo = "";

	private String propDesi = "";

	private String propDate = "";

	private String suppName = "";

	private String suppCoinNo = "";

	private String suppDesi = "";

	private String suppDate = "";

	private String scoName = "";

	private String scoCoinNo = "";

	private String scoDesi = "";

	private String scoDate = "";

	private String rcoName = "";

	private String rcoCoinNo = "";

	private String rcoDesi = "";

	private String rcoDate = "";

	private String ccoName = "";

	// private String appCoinNo3 = "";
	private String ccoDesi = "";

	private String ccoDate = "";

	private String appName = "";

	private String meetingMinutes = "";

	private String creditCommittee = "";

	public String getWaiverReason() {
		return waiverReason;
	}

	public void setWaiverReason(String waiverReason) {
		this.waiverReason = waiverReason;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropSignNo() {
		return propSignNo;
	}

	public void setPropSignNo(String propSignNo) {
		this.propSignNo = propSignNo;
	}

	public String getPropDesi() {
		return propDesi;
	}

	public void setPropDesi(String propDesi) {
		this.propDesi = propDesi;
	}

	public String getPropDate() {
		return propDate;
	}

	public void setPropDate(String propDate) {
		this.propDate = propDate;
	}

	public String getSuppName() {
		return suppName;
	}

	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	public String getSuppCoinNo() {
		return suppCoinNo;
	}

	public void setSuppCoinNo(String suppCoinNo) {
		this.suppCoinNo = suppCoinNo;
	}

	public String getSuppDesi() {
		return suppDesi;
	}

	public void setSuppDesi(String suppDesi) {
		this.suppDesi = suppDesi;
	}

	public String getSuppDate() {
		return suppDate;
	}

	public void setSuppDate(String suppDate) {
		this.suppDate = suppDate;
	}

	public String getScoName() {
		return scoName;
	}

	public void setScoName(String scoName) {
		this.scoName = scoName;
	}

	public String getScoCoinNo() {
		return scoCoinNo;
	}

	public void setScoCoinNo(String scoCoinNo) {
		this.scoCoinNo = scoCoinNo;
	}

	public String getScoDesi() {
		return scoDesi;
	}

	public void setScoDesi(String scoDesi) {
		this.scoDesi = scoDesi;
	}

	public String getScoDate() {
		return scoDate;
	}

	public void setScoDate(String scoDate) {
		this.scoDate = scoDate;
	}

	public String getRcoName() {
		return rcoName;
	}

	public void setRcoName(String rcoName) {
		this.rcoName = rcoName;
	}

	public String getRcoCoinNo() {
		return rcoCoinNo;
	}

	public void setRcoCoinNo(String rcoCoinNo) {
		this.rcoCoinNo = rcoCoinNo;
	}

	public String getRcoDesi() {
		return rcoDesi;
	}

	public void setRcoDesi(String rcoDesi) {
		this.rcoDesi = rcoDesi;
	}

	public String getRcoDate() {
		return rcoDate;
	}

	public void setRcoDate(String rcoDate) {
		this.rcoDate = rcoDate;
	}

	public String getCcoName() {
		return ccoName;
	}

	public void setCcoName(String ccoName) {
		this.ccoName = ccoName;
	}

	public String getCcoDesi() {
		return ccoDesi;
	}

	public void setCcoDesi(String ccoDesi) {
		this.ccoDesi = ccoDesi;
	}

	public String getCcoDate() {
		return ccoDate;
	}

	public void setCcoDate(String ccoDate) {
		this.ccoDate = ccoDate;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMeetingMinutes() {
		return meetingMinutes;
	}

	public void setMeetingMinutes(String meetingMinutes) {
		this.meetingMinutes = meetingMinutes;
	}

	public String getCreditCommittee() {
		return creditCommittee;
	}

	public void setCreditCommittee(String creditCommittee) {
		this.creditCommittee = creditCommittee;
	}

	public String getMeetingDate() {
		return meetingDate;
	}

	public void setMeetingDate(String meetingDate) {
		this.meetingDate = meetingDate;
	}

	private String meetingDate = "";

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
		// {"limitProfile","com.integrosys.cms.ui.genscc.LimitProfileMapper"},
				{ "waiverReq", "com.integrosys.cms.ui.genreqwaiver.RequestWaiverMapper" } };
		return input;
	}

}
