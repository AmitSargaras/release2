/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCertificateSearchResult.java,v 1.2 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class contains the search result for cc certificate
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class CCCertificateSearchResult implements Serializable {
	private long ccCertID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String trxID = null;

	private String trxStatus = null;

	/**
	 * Get the cc certificate ID
	 * @return long - the cc certificate
	 */
	public long getCCCertID() {
		return this.ccCertID;
	}

	/**
	 * Get the trx ID
	 * @return String - the trx ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the trx status
	 * @return String - the trx status
	 */
	public String getTrxStatus() {
		return this.trxStatus;
	}

	/**
	 * Set the cc certificate ID
	 * @param aCCCertID of long type
	 */
	public void setCCCertID(long aCCCertID) {
		this.ccCertID = aCCCertID;
	}

	/**
	 * Set the trx ID.
	 * @param aTrxID - String
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the trx status.
	 * @param aTrxStatus - String
	 */
	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
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
