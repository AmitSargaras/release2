/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCertificateSummary.java,v 1.8 2005/11/21 11:05:52 hshii Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class contains the attribute required for the c/c certificate summary
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/11/21 11:05:52 $ Tag: $Name: $
 */
public class CCCertificateSummary implements Serializable {
	private CCCheckListSummary checkListSummary;

	private long ccCertID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date lastCCCUpdateDate;

	private String cccTrxID;

	public CCCheckListSummary getCertificateSummary() {
		return this.checkListSummary;
	}

	public String getCustCategory() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getCustCategory();
		}
		return null;
	}

	public long getSubProfileID() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getSubProfileID();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getLegalID() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getLegalID();
		}
		return null;
	}

	public String getLegalName() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getLegalName();
		}
		return null;
	}

	public String getDomicileCtry() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getDomicileCtry();
		}
		return null;
	}

	public String getOrgCode() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getOrgCode();
		}
		return null;
	}

	public String getGovernLaw() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getGovernLaw();
		}
		return null;
	}

	public String getLimitBkgLoc() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getLimitBkgLoc();
		}
		return null;
	}

	public String getLegalConstitution() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getLegalConstitution();
		}
		return null;
	}

	public long getCheckListID() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getCheckListID();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getCheckListStatus() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getCheckListStatus();
		}
		return null;
	}

	public String getPledgorReference() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getPledgorReference();
		}
		return null;
	}

	public boolean getSameCountryInd() {
		if (getCertificateSummary() != null) {
			return getCertificateSummary().getSameCountryInd();
		}
		return false;
	}

	public void setCertificateSummary(CCCheckListSummary aCheckListSummary) {
		this.checkListSummary = aCheckListSummary;
	}

	public Date getLastCCCUpdateDate() {
		return this.lastCCCUpdateDate;
	}

	public void setLastCCCUpdateDate(Date lastCCCUpdateDate) {
		this.lastCCCUpdateDate = lastCCCUpdateDate;
	}

	public String getCccTrxID() {
		return this.cccTrxID;
	}

	public void setCccTrxID(String cccTrxID) {
		this.cccTrxID = cccTrxID;
	}

	public boolean allowGenerateCCC() {
		/*
		 * if
		 * (((getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_COMPLETED
		 * )) ||
		 * (getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_CERT_ALLOWED
		 * )) ||
		 * (getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_WAIVED)))
		 * && getSameCountryInd())
		 */
		// CMS-210 && CMS-1134
        /*
		if ((getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_COMPLETED))
				|| (getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_CERT_ALLOWED))
				|| (getCheckListStatus().equals(ICMSConstant.STATE_CHECKLIST_WAIVED)))

		{
			return true;
		}
		return false;
		*/

        //Allowed Check List Status - Add More Here
        java.util.HashMap STATUS = new java.util.HashMap();
        STATUS.put(ICMSConstant.STATE_CHECKLIST_IN_PROGRESS, null);

        if (STATUS.containsKey(getCheckListStatus())) return true;

        return false;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
