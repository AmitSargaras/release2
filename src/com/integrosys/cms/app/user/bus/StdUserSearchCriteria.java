/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/bus/StdUserSearchCriteria.java,v 1.3 2005/10/25 06:33:02 lyng Exp $
 */
package com.integrosys.cms.app.user.bus;

import com.integrosys.component.user.app.bus.OBCommonUserSearchCriteria;
import com.integrosys.component.bizstructure.app.bus.ITeam;

import java.util.List;
import java.util.ArrayList;

/**
 * This class is to provide search criteria for team maintenance, with support
 * for multiple-role users(esp. CPC & CPC Custodian).
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/25 06:33:02 $ Tag: $Name: $
 */
public class StdUserSearchCriteria extends OBCommonUserSearchCriteria {
	//private String m_teamTypeId;

	private String m_memshipTypeId;

	//private String m_maintainTeamId;
	
	public StdUserSearchCriteria() {
	}

	/**
	 * Constructs the user search criteria given the common user search
	 * criteria.
	 * 
	 * @param obCriteria of type OBCommonUserSearchCriteria
	 */
	public StdUserSearchCriteria(OBCommonUserSearchCriteria obCriteria) {
		super();
		this.setAssignmentType(obCriteria.getAssignmentType());
		this.setEmployeeId(obCriteria.getEmployeeId());
		this.setRoleTypeID(obCriteria.getRoleTypeID());
		this.setTeamMembershipTypeID(obCriteria.getTeamMembershipTypeID());
		this.setUserName(obCriteria.getUserName());
		this.setUserRoleTypeID(obCriteria.getUserRoleTypeID());
	}
	
	/**
	 * Set user membership type id.
	 * 
	 * @param memshipTypeId of type String
	 */
	public void setMemshipTypeId(String memshipTypeId) {
		m_memshipTypeId = memshipTypeId;
	}

	/**
	 * Get user membership type id.
	 * 
	 * @return String
	 */
	public String getMemshipTypeId() {
		return m_memshipTypeId;
	}

}
