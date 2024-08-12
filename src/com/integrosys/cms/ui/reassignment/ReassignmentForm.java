/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/reassignment/ReassignmentForm.java,v 1.1 2004/09/21 09:16:45 hshii Exp $
 */
package com.integrosys.cms.ui.reassignment;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/21 09:16:45 $ Tag: $Name: $
 */
public class ReassignmentForm extends TrxContextForm implements Serializable {
	private String dealTrxID = "";

	private String cccTrxID = "";

	private String sccTrxID = "";

	private String dealNo = "";

	private String ccChecklistID = "";

	private String scChecklistID = "";

	private String reassignmentType = "";

	private String searchType = "";

	public String getDealTrxID() {
		return dealTrxID;
	}

	public void setDealTrxID(String dealTrxID) {
		this.dealTrxID = dealTrxID;
	}

	public String getCccTrxID() {
		return cccTrxID;
	}

	public void setCccTrxID(String cccTrxID) {
		this.cccTrxID = cccTrxID;
	}

	public String getSccTrxID() {
		return sccTrxID;
	}

	public void setSccTrxID(String sccTrxID) {
		this.sccTrxID = sccTrxID;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	public String getCcChecklistID() {
		return ccChecklistID;
	}

	public void setCcChecklistID(String ccChecklistID) {
		this.ccChecklistID = ccChecklistID;
	}

	public String getScChecklistID() {
		return scChecklistID;
	}

	public void setScChecklistID(String scChecklistID) {
		this.scChecklistID = scChecklistID;
	}

	public String getReassignmentType() {
		return reassignmentType;
	}

	public void setReassignmentType(String reassignmentType) {
		this.reassignmentType = reassignmentType;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public void reset() {
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (key,Mapperclassname)
	 * 
	 * @return One-dimesnional String Array
	 */

	public String[][] getMapper() {
		String[][] input = { { "taskSearchCriteria", "com.integrosys.cms.ui.reassignment.ReassignmentMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };

		return input;
	}
}
