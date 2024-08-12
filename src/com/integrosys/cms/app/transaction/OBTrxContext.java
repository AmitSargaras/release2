/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBTrxContext.java,v 1.2 2003/07/29 10:06:59 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import java.util.List;

import com.integrosys.cms.app.common.OBContext;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This class implementing the ITrxContext Interface
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/29 10:06:59 $ Tag: $Name: $
 */
public class OBTrxContext extends OBContext implements ITrxContext {
	private long teamMembershipID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean stpAllowed = false;
	
	private List functionGroupList;

	public OBTrxContext() {
		super();
	}

	public OBTrxContext(ICommonUser aUserID, ITeam aTeamID) {
		super(aUserID, aTeamID);
	}

	public OBTrxContext(ICommonUser aUserID, ITeam aTeamID, long teamMembershipID) {
		super.setTeam(aTeamID);
		super.setUser(aUserID);
		setTeamMembershipID(teamMembershipID);
	}

	public List getFunctionGroupList() {
		return functionGroupList;
	}

	public boolean getStpAllowed() {
		return stpAllowed;
	}

	public long getTeamMembershipID() {
		return teamMembershipID;
	}

	public void setFunctionGroupList(List functionGroupList) {
		this.functionGroupList = functionGroupList;
	}

	public void setStpAllowed(boolean stpAllowed) {
		this.stpAllowed = stpAllowed;
	}

	public void setTeamMembershipID(long value) {
		teamMembershipID = value;
	}
}