/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/OBCCCertificateTrxValue.java,v 1.1 2003/08/04 12:56:09 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for ICCCertificateTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/04 12:56:09 $ Tag: $Name: $
 */
public class OBCCCertificateTrxValue extends OBCMSTrxValue implements ICCCertificateTrxValue {
	private ICCCertificate ccCertificate = null;

	private ICCCertificate stagingCCCertificate = null;

	/**
	 * Default Constructor
	 */
	public OBCCCertificateTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBCCCertificateTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the cc certificate busines entity
	 * 
	 * @return ICCCertificate
	 */
	public ICCCertificate getCCCertificate() {
		return this.ccCertificate;
	}

	/**
	 * Get the staging cc certificate business entity
	 * 
	 * @return ICCCertificate
	 */
	public ICCCertificate getStagingCCCertificate() {
		return this.stagingCCCertificate;
	}

	/**
	 * Set the cc certificate busines entity
	 * 
	 * @param anICCCertificate is of type ICCCertificate
	 */
	public void setCCCertificate(ICCCertificate anICCCertificate) {
		this.ccCertificate = anICCCertificate;
	}

	/**
	 * Set the staging cc certificate business entity
	 * 
	 * @param anICCCertificate is of type ICCCertificate
	 */
	public void setStagingCCCertificate(ICCCertificate anICCCertificate) {
		this.stagingCCCertificate = anICCCertificate;
	}
}