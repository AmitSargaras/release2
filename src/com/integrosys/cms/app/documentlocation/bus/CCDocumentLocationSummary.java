/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/CCDocumentLocationSummary.java,v 1.3 2005/09/08 09:04:59 hshii Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;

/**
 * This class contains the attribute required for the c/c document location
 * summary
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/09/08 09:04:59 $ Tag: $Name: $
 */
public class CCDocumentLocationSummary implements Serializable {
	private CCCheckListSummary summary = null;

	private boolean isViewable = false;

	public CCDocumentLocationSummary(CCCheckListSummary aSummary) {
		setSummary(aSummary);
	}

	public CCCheckListSummary getSummary() {
		return this.summary;
	}

	public String getDocumentLocationCategory() {
		if (getSummary() != null) {
			return getSummary().getCustCategory();
		}
		return null;
	}

	public String getLegalID() {
		if (getSummary() != null) {
			return getSummary().getLegalID();
		}
		return null;
	}

	public String getLegalName() {
		if (getSummary() != null) {
			return getSummary().getLegalName();
		}
		return null;
	}

	public String getDocumentLocationCountry() {
		if (getSummary() != null) {
			return getSummary().getDomicileCtry();
		}
		return null;
	}

	public String getDocumentOrgCode() {
		if (getSummary() != null) {
			return getSummary().getOrgCode();
		}
		return null;
	}

	public String getGovernLaw() {
		if (getSummary() != null) {
			return getSummary().getGovernLaw();
		}
		return null;
	}

	public String getCheckListStatus() {
		if (getSummary() != null) {
			return getSummary().getCheckListStatus();
		}
		return null;
	}

	public String getPledgorReference() {
		if (getSummary() != null) {
			return getSummary().getPledgorReference();
		}
		return null;
	}

	private void setSummary(CCCheckListSummary aSummary) {
		this.summary = aSummary;
	}

	public boolean getIsViewable() {
		return this.isViewable;
	}

	public void setIsViewable(boolean isViewable) {
		this.isViewable = isViewable;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
