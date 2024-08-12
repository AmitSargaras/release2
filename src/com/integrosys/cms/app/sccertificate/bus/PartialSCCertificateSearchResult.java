/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/PartialSCCertificateSearchResult.java,v 1.1 2005/05/12 02:42:46 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class contains the search result for pscc certificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/05/12 02:42:46 $ Tag: $Name: $
 */
public class PartialSCCertificateSearchResult implements Serializable {
	private long psccID = ICMSConstant.LONG_INVALID_VALUE;

	private String trxID = null;

	private String trxStatus = null;

	/**
	 * Get SCC ID.
	 * 
	 * @return long
	 */
	public long getPSCCID() {
		return psccID;
	}

	/**
	 * Set SCC ID.
	 * 
	 * @param psccID of type long
	 */
	public void setPSCCID(long psccID) {
		this.psccID = psccID;
	}

	/**
	 * Get transaction id.
	 * 
	 * @return String
	 */
	public String getTrxID() {
		return trxID;
	}

	/**
	 * Set transaction id.
	 * 
	 * @param trxID of type String
	 */
	public void setTrxID(String trxID) {
		this.trxID = trxID;
	}

	/**
	 * Get transaction status.
	 * 
	 * @return long
	 */
	public String getTrxStatus() {
		return trxStatus;
	}

	/**
	 * Set transaction status.
	 * 
	 * @param trxStatus of type String
	 */
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
