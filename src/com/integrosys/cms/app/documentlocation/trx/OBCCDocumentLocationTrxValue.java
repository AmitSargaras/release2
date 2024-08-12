/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/OBCCDocumentLocationTrxValue.java,v 1.1 2004/02/17 02:12:37 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for ICCDocumentLocationTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:37 $ Tag: $Name: $
 */
public class OBCCDocumentLocationTrxValue extends OBCMSTrxValue implements ICCDocumentLocationTrxValue {
	private ICCDocumentLocation cCDocumentLocation = null;

	private ICCDocumentLocation stagingCCDocumentLocation = null;

	/**
	 * Default Constructor
	 */
	public OBCCDocumentLocationTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBCCDocumentLocationTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the CC document location business entity
	 * 
	 * @return ICCDocumentLocation
	 */
	public ICCDocumentLocation getCCDocumentLocation() {
		return this.cCDocumentLocation;
	}

	/**
	 * Get the staging CC document location business entity
	 * 
	 * @return ICCDocumentLocation
	 */
	public ICCDocumentLocation getStagingCCDocumentLocation() {
		return this.stagingCCDocumentLocation;
	}

	/**
	 * Set the CC document location business entity
	 * 
	 * @param value is of type ICCDocumentLocation
	 */
	public void setCCDocumentLocation(ICCDocumentLocation value) {
		this.cCDocumentLocation = value;
	}

	/**
	 * Set the staging CC document location business entity
	 * 
	 * @param value is of type ICCDocumentLocation
	 */
	public void setStagingCCDocumentLocation(ICCDocumentLocation value) {
		this.stagingCCDocumentLocation = value;
	}
}