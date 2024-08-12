/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CollateralCheckListSummary.java,v 1.22 2006/08/14 10:57:03 jzhai Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This class contains the attribute required for the collateral checklist
 * summary
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/08/14 10:57:03 $ Tag: $Name: $
 */
public class FacilityCheckListSummary implements ICheckListSummary, Comparable {

	private static final long serialVersionUID = 4226283559478289891L;

	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String collateralRef = null;

	private ICollateralType facType = null;

	private ICollateralSubType facSubType = null;

	private String facName = null;

	private String collateralLocation = null;

	private String securityOrganization = null;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	// private String checkListStatus =
	// ICMSConstant.STATE_CHECKLIST_IN_PROGRESS;
	private String checkListStatus = ICMSConstant.STATE_CHECKLIST_NEW;

	private ILimit[] limitList = null;

	private String trxID = null;

	private String trxStatus = null;

	private String trxFromState = null;

	private boolean sameCountryInd = false;

	private boolean allowDeleteInd = false;

	private ICheckListTrxValue trxValue;

	// --------- R1.5 CR35 ------------
	private String custCategory = null;

	private ICoBorrowerLimit[] coborrowerLimitList = null;

	private long subProfileID = ICMSConstant.LONG_INVALID_VALUE;

	// --------------------------------

	private long leSubProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	// --------------------------------
	private String applicationType = null;

	// --------------- Getter and Setters -----------------

	public long getLeSubProfileID() {
		return leSubProfileID;
	}

	public void setLeSubProfileID(long leSubProfileID) {
		this.leSubProfileID = leSubProfileID;
	}

	/*
	 * Checklist reference ID that will displayed to end-users. Will always be
	 * the actual checklist ID
	 */
	private long checklistReferenceID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the collateral ID
	 * @return long - the collateral ID
	 */
	public long getCollateralID() {
		return this.collateralID;
	}

	/**
	 * Get the collateral reference
	 * @return String - the collateral reference
	 */
	public String getCollateralRef() {
		return this.collateralRef;
	}

//	/**
//	 * Get the collateral type
//	 * @return ICollateralType - the collateral type
//	 */
//	public ICollateralType getCollateralType() {
//		return this.collateralType;
//	}
//
//	/**
//	 * Get the collateral sub type
//	 * @return ICollateralSubType - the collateral subtype
//	 */
//	public ICollateralSubType getCollateralSubType() {
//		return this.collateralSubType;
//	}
//
//	public String getCollateralName() {
//		return collateralName;
//	}
//
//	public void setCollateralName(String collateralName) {
//		this.collateralName = collateralName;
//	}

	/**
	 * Get the collateral location
	 * @return String - the collateral location
	 */
	public String getCollateralLocation() {
		return this.collateralLocation;
	}

	public String getSecurityOrganization() {
		return securityOrganization;
	}

	/**
	 * Get the checklist ID
	 * @return long - the checklist ID
	 */
	public long getCheckListID() {
		return this.checkListID;
	}

	/**
	 * Get the checklist status
	 * @return String - the checklist status
	 **/
	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	/**
	 * Get the list of limit under a collateral
	 * @return ILimit[] - the list of limits
	 */
	public ILimit[] getLimitList() {
		return this.limitList;
	}

	/**
	 * Helper method to get the list of limit references
	 * @return String[] - the list of limit references
	 */
	public String[] getLimitRefList() {
		ILimit[] list = getLimitList();
		if ((list != null) && (list.length > 0)) {
			String[] strList = new String[list.length];
			for (int ii = 0; ii < list.length; ii++) {
				strList[ii] = list[ii].getLimitRef();
			}
			return strList;
		}
		return null;
	}

	public String getTrxID() {
		return this.trxID;
	}

	public String getTrxStatus() {
		return this.trxStatus;
	}

	public boolean getSameCountryInd() {
		return this.sameCountryInd;
	}

	public boolean getAllowDeleteInd() {
		return this.allowDeleteInd;
	}

	/**
	 * Set the collateral ID
	 * @param aCollateralID of long type
	 */
	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	/**
	 * Set the collateral reference
	 * @param aCollateralRef of String type
	 */
	public void setCollateralRef(String aCollateralRef) {
		this.collateralRef = aCollateralRef;
	}

//	/**
//	 * Set the collateral type
//	 * @param aCollateralType of ICollateralType type
//	 */
//	public void setCollateralType(ICollateralType aCollateralType) {
//		this.collateralType = aCollateralType;
//	}
//
//	/**
//	 * Set the collateral subtype
//	 * @param aCollateralSubType of ICollateralSubType type
//	 */
//	public void setCollateralSubType(ICollateralSubType aCollateralSubType) {
//		this.collateralSubType = aCollateralSubType;
//	}

	/**
	 * Set the collateral location
	 * @param aCollateralLocation of String type
	 */
	public void setCollateralLocation(String aCollateralLocation) {
		this.collateralLocation = aCollateralLocation;
	}



	public ICollateralType getFacType() {
		return facType;
	}

	public void setFacType(ICollateralType facType) {
		this.facType = facType;
	}

	public ICollateralSubType getFacSubType() {
		return facSubType;
	}

	public void setFacSubType(ICollateralSubType facSubType) {
		this.facSubType = facSubType;
	}

	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	public void setSecurityOrganization(String securityOrganization) {
		this.securityOrganization = securityOrganization;
	}

	/**
	 * Set the checklist ID
	 * @param aCheckListID - long
	 */
	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus) {
		this.checkListStatus = aCheckListStatus;
	}

	/**
	 * Set the list of limit under the collateral
	 * @param aLimitList of ILimit[] type
	 */
	public void setLimitList(ILimit[] aLimitList) {
		this.limitList = aLimitList;
	}

	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	public void setTrxFromState(String fromState) {
		this.trxFromState = fromState;
	}

	public String getTrxFromState() {
		return this.trxFromState;
	}

	public void setSameCountryInd(boolean aSameCountryInd) {
		this.sameCountryInd = aSameCountryInd;
	}

	public boolean isEditable() {
		if (!getSameCountryInd()) {
			return false;
		}
		if (ICMSConstant.STATE_CHECKLIST_IN_PROGRESS.equals(getCheckListStatus())) {
			return true;
		}
		return false;
	}

	public void setAllowDeleteInd(boolean anAllowDeleteInd) {
		this.allowDeleteInd = anAllowDeleteInd;
	}

	/**
	 * Get checklist reference ID.
	 * 
	 * @return long
	 */
	public long getCheckListReferenceID() {
		return checklistReferenceID;
	}

	/**
	 * Set checklist reference ID.
	 * 
	 * @param refID of type long
	 */
	public void setCheckListReferenceID(long refID) {
		this.checklistReferenceID = refID;
	}

	/**
	 * Get checklist transaction value.
	 * 
	 * @return ICheckListTrxValue
	 */
	public ICheckListTrxValue getTrxValue() {
		return trxValue;
	}

	/**
	 * Set checklist transaction value.
	 * 
	 * @param trxValue of type ICheckListTrxValue
	 */
	public void setTrxValue(ICheckListTrxValue trxValue) {
		this.trxValue = trxValue;
	}

	private String disableTaskInd = null;

	public String getDisableTaskInd() {
		return this.disableTaskInd;
	}

	public void setDisableTaskInd(String aDisableTaskInd) {
		this.disableTaskInd = aDisableTaskInd;
	}

	private ICMSTrxValue taskTrx;

	public void setTaskTrx(ICMSTrxValue taskStageTrx) {
		this.taskTrx = taskStageTrx;
	}

	public ICMSTrxValue getTaskTrx() {
		return this.taskTrx;

	}

	// private ICMSTrxValue checklistTrx;

	// public void setChecklistTrx(ICMSTrxValue checklistTrx) {
	// this.checklistTrx = checklistTrx;
	// }

	// public ICMSTrxValue getChecklistTrx() {
	// return this.checklistTrx;
	// }

	// ---- R1.5 CR35 ----
	public String getCustCategory() {
		return this.custCategory;
	}

	public void setCustCategory(String aCustCategory) {
		this.custCategory = aCustCategory;
	}

	public ICoBorrowerLimit[] getCoborrowerLimitList() {
		return coborrowerLimitList;
	}

	public void setCoborrowerLimitList(ICoBorrowerLimit[] coborrowerLimitList) {
		this.coborrowerLimitList = coborrowerLimitList;
	}

	public long getSubProfileID() {
		return subProfileID;
	}

	public void setSubProfileID(long subProfileID) {
		this.subProfileID = subProfileID;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	// -------------------

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public int compareTo(Object other) {
		String otherCollateralRef = (other == null) ? null : ((FacilityCheckListSummary) other).getCollateralRef();

		if (this.collateralRef == null) {
			return (otherCollateralRef == null) ? 0 : -1;
		}

		return (otherCollateralRef == null) ? 1 : this.collateralRef.compareTo(otherCollateralRef);
	}
}
