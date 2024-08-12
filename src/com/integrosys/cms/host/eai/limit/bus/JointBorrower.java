package com.integrosys.cms.host.eai.limit.bus;

import java.util.Vector;

import com.integrosys.cms.host.eai.customer.bus.MainProfile;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class JointBorrower implements java.io.Serializable {

	private long cmsId;

	private long cmsLimitProfileId;

	private long cmsSubProfileId;

	private String CIFId;

	private String cifSource;

	private long subProfileId;

	private String AANumber;

	private String updateStatusIndicator;

	private String changeIndicator;

	private Vector creditGrade;

	private MainProfile mainProfile;

	/**
	 * @return the mainProfile
	 */
	public final MainProfile getMainProfile() {
		return mainProfile;
	}

	/**
	 * @param mainProfile the mainProfile to set
	 */
	public final void setMainProfile(MainProfile mainProfile) {
		this.mainProfile = mainProfile;
	}

	public long getCmsSubProfileId() {
		return cmsSubProfileId;
	}

	public void setCmsSubProfileId(long cmsSubProfileId) {
		this.cmsSubProfileId = cmsSubProfileId;
	}

	public long getCmsId() {
		return cmsId;
	}

	public void setCmsId(long cmsId) {
		this.cmsId = cmsId;
	}

	public long getCmsLimitProfileId() {
		return cmsLimitProfileId;
	}

	public void setCmsLimitProfileId(long cmsLimitProfileId) {
		this.cmsLimitProfileId = cmsLimitProfileId;
	}

	public String getCIFId() {
		return CIFId;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public String getCifSource() {
		return cifSource;
	}

	public void setCifSource(String cifSource) {
		this.cifSource = cifSource;
	}

	public long getSubProfileId() {
		//return IEaiConstant.SUBPROFILE_ID;
		return subProfileId;
	}

	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public String getAANumber() {
		return AANumber;
	}

	public void setAANumber(String AANumber) {
		this.AANumber = AANumber;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public Vector getCreditGrade() {
		return creditGrade;
	}

	public void setCreditGrade(Vector creditGrade) {
		this.creditGrade = creditGrade;
	}

}
