/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListCustodianStatus.java,v 1.3 2004/08/03 08:55:30 btan Exp $
 */

package com.integrosys.cms.app.checklist.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class encapsulates the 3 statuses, i.e. CPC, CPC Custodian and Custodian
 * statuses.
 * 
 * @author $Author: btan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/03 08:55:30 $ Tag: $Name: $
 */
public class CheckListCustodianStatus implements Serializable {

	String stageCPCStatus = null;

	String stageCPCCustStatus = null;

	String cpcStatus = null;

	String cpcCustStatus = null;

	String custStatus = null;

	String trxStatus = null;

	public CheckListCustodianStatus(String stageCPCStatus, String stageCPCCustStatus, String cpcStatus,
			String cpcCustStatus, String custStatus, String trxStatus) {
		this.stageCPCStatus = stageCPCStatus;
		this.stageCPCCustStatus = stageCPCCustStatus;
		this.cpcStatus = cpcStatus;
		this.cpcCustStatus = cpcCustStatus;
		this.custStatus = custStatus;
		this.trxStatus = trxStatus;
	}

	public String getStageCPCStatus() {
		return stageCPCStatus;
	}

	public String getStageCPCCustStatus() {
		return stageCPCCustStatus;
	}

	public String getCPCStatus() {
		return cpcStatus;
	}

	public String getCPCCustStatus() {
		return cpcCustStatus;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public String getTrxStatus() {
		return trxStatus;
	}

	public void setStageCPCStatus(String stageCPCStatus) {
		this.stageCPCStatus = stageCPCStatus;
	}

	public void setStageCPCCustStatus(String stageCPCCustStatus) {
		this.stageCPCCustStatus = stageCPCCustStatus;
	}

	public void setCPCStatus(String cpcStatus) {
		this.cpcStatus = cpcStatus;
	}

	public void setCPCCustStatus(String cpcCustStatus) {
		this.cpcCustStatus = cpcCustStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public void setTrxStatus(String trxStatus) {
		this.trxStatus = trxStatus;
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
