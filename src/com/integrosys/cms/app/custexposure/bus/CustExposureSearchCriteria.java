package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierSearchCriteria;
import com.integrosys.component.user.app.bus.ICommonUser;

import java.util.List;

public class CustExposureSearchCriteria extends CustGrpIdentifierSearchCriteria {

	private static final long serialVersionUID = 1L;

	private String leID;
	private String sourceId;
	private String subProfileID;
	private String groupCCINo;
	private boolean CustomerSeach;
	private long teamID;
	private List collateralIDs;
	private ICommonUser user;
	private String lEReference;


	/**
	 * @return the lEReference
	 */
	public String getLEReference() {
		return lEReference;
	}

	/**
	 * @param reference the lEReference to set
	 */
	public void setLEReference(String reference) {
		lEReference = reference;
	}

	public ICommonUser getUser() {
		return user;
	}

	public void setUser(ICommonUser user) {
		this.user = user;
	}

	public String getLeID() {
		return leID;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public List getCollateralIDs() {
		return collateralIDs;
	}

	public void setCollateralIDs(List collateralIDs) {
		this.collateralIDs = collateralIDs;
	}

	public long getTeamID() {
		return teamID;
	}

	public void setTeamID(long teamID) {
		this.teamID = teamID;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public boolean getCustomerSeach() {
		return CustomerSeach;
	}

	public void setCustomerSeach(boolean customerSeach) {
		CustomerSeach = customerSeach;
	}

	public String getGroupCCINo() {
		return groupCCINo;
	}

	public void setGroupCCINo(String groupCCINo) {
		this.groupCCINo = groupCCINo;
	}

}
