/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/OBWaiverRequestTrxValue.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for IWaiverRequestTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public class OBWaiverRequestTrxValue extends OBCMSTrxValue implements IWaiverRequestTrxValue {
	private IWaiverRequest waiverRequest = null;

	private IWaiverRequest stagingWaiverRequest = null;

	/**
	 * Default Constructor
	 */
	public OBWaiverRequestTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBWaiverRequestTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the waiver request business entity
	 * 
	 * @return IWaiverRequest
	 */
	public IWaiverRequest getWaiverRequest() {
		return this.waiverRequest;
	}

	/**
	 * Get the staging waiver request business entity
	 * 
	 * @return IWaiverRequest
	 */
	public IWaiverRequest getStagingWaiverRequest() {
		return this.stagingWaiverRequest;
	}

	/**
	 * Set the waiver request business entity
	 * 
	 * @param value is of type IWaiverRequest
	 */
	public void setWaiverRequest(IWaiverRequest value) {
		this.waiverRequest = value;
	}

	/**
	 * Set the staging waiver request business entity
	 * 
	 * @param value is of type IWaiverRequest
	 */
	public void setStagingWaiverRequest(IWaiverRequest value) {
		this.stagingWaiverRequest = value;
	}
}