/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/OBDDNTrxValue.java,v 1.2 2005/08/20 10:25:39 hshii Exp $
 */
package com.integrosys.cms.app.ddn.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for IDDNTrxValue
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/08/20 10:25:39 $ Tag: $Name: $
 */
public class OBDDNTrxValue extends OBCMSTrxValue implements IDDNTrxValue {
	private IDDN ddn = null;

	private IDDN stagingDDN = null;

	private boolean isLatestActive = false;

	/**
	 * Default Constructor
	 */
	public OBDDNTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBDDNTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the sc certificate busines entity
	 * 
	 * @return IDDN
	 */
	public IDDN getDDN() {
		return this.ddn;
	}

	/**
	 * Get the staging sc certificate business entity
	 * 
	 * @return IDDN
	 */
	public IDDN getStagingDDN() {
		return this.stagingDDN;
	}

	/**
	 * Set the sc certificate busines entity
	 * 
	 * @param anIDDN is of type IDDN
	 */
	public void setDDN(IDDN anIDDN) {
		this.ddn = anIDDN;
	}

	/**
	 * Set the staging sc certificate business entity
	 * 
	 * @param anIDDN is of type IDDN
	 */
	public void setStagingDDN(IDDN anIDDN) {
		this.stagingDDN = anIDDN;
	}

	/**
	 * Get the indicator for retrieving latest active remarks and user info
	 * 
	 * @return boolean
	 */
	public boolean getIsLatestActive() {
		return this.isLatestActive;
	}

	/**
	 * Set the indicator for retrieving latest active remarks and user info
	 * 
	 * @param isLatestActive is of type boolean
	 */
	public void setIsLatestActive(boolean isLatestActive) {
		this.isLatestActive = isLatestActive;
	}
}