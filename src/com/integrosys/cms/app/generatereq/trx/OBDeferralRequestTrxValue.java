/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/OBDeferralRequestTrxValue.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.generatereq.bus.IDeferralRequest;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for IDeferralRequestTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public class OBDeferralRequestTrxValue extends OBCMSTrxValue implements IDeferralRequestTrxValue {
	private IDeferralRequest deferralRequest = null;

	private IDeferralRequest stagingDeferralRequest = null;

	/**
	 * Default Constructor
	 */
	public OBDeferralRequestTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBDeferralRequestTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the deferral request business entity
	 * 
	 * @return IDeferralRequest
	 */
	public IDeferralRequest getDeferralRequest() {
		return this.deferralRequest;
	}

	/**
	 * Get the staging deferral request business entity
	 * 
	 * @return IDeferralRequest
	 */
	public IDeferralRequest getStagingDeferralRequest() {
		return this.stagingDeferralRequest;
	}

	/**
	 * Set the deferral request business entity
	 * 
	 * @param value is of type IDeferralRequest
	 */
	public void setDeferralRequest(IDeferralRequest value) {
		this.deferralRequest = value;
	}

	/**
	 * Set the staging deferral request business entity
	 * 
	 * @param value is of type IDeferralRequest
	 */
	public void setStagingDeferralRequest(IDeferralRequest value) {
		this.stagingDeferralRequest = value;
	}
}