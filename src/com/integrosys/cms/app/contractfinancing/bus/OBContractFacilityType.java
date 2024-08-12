package com.integrosys.cms.app.contractfinancing.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBContractFacilityType implements IContractFacilityType {

	private long facilityTypeID = ICMSConstant.LONG_INVALID_VALUE;

	private String facilityType;

	private String facilityTypeOthers;

	private Date facilityDate;

	private float moa = ICMSConstant.FLOAT_INVALID_VALUE;

	private Amount maxCap;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean hasChild;

	private boolean isDeleted;

	/**
	 * Default Constructor
	 */
	public OBContractFacilityType() {
	}

	public long getFacilityTypeID() {
		return facilityTypeID;
	}

	public void setFacilityTypeID(long facilityTypeID) {
		this.facilityTypeID = facilityTypeID;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getFacilityTypeOthers() {
		return facilityTypeOthers;
	}

	public void setFacilityTypeOthers(String facilityTypeOthers) {
		this.facilityTypeOthers = facilityTypeOthers;
	}

	public Date getFacilityDate() {
		return facilityDate;
	}

	public void setFacilityDate(Date facilityDate) {
		this.facilityDate = facilityDate;
	}

	public float getMoa() {
		return moa;
	}

	public void setMoa(float moa) {
		this.moa = moa;
	}

	public Amount getMaxCap() {
		return maxCap;
	}

	public void setMaxCap(Amount maxCap) {
		this.maxCap = maxCap;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
