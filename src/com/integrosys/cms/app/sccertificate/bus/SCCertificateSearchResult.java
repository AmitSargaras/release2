/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SCCertificateSearchResult.java,v 1.1 2005/05/11 11:45:48 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class contains the search result for scc certificate
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/05/11 11:45:48 $ Tag: $Name: $
 */
public class SCCertificateSearchResult implements Serializable {
	private long sccID = ICMSConstant.LONG_INVALID_VALUE;

	private String trxID = null;

	private String trxStatus = null;

	/**
	 * Get SCC ID.
	 * 
	 * @return long
	 */
	public long getSCCID() {
		return sccID;
	}

	/**
	 * Set SCC ID.
	 * 
	 * @param sccID of type long
	 */
	public void setSCCID(long sccID) {
		this.sccID = sccID;
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
