/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/OBPartialSCCertificateTrxValue.java,v 1.1 2003/08/11 12:51:02 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for IPartialSCCertificateTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:51:02 $ Tag: $Name: $
 */
public class OBPartialSCCertificateTrxValue extends OBCMSTrxValue implements IPartialSCCertificateTrxValue {
	private IPartialSCCertificate pscCertificate = null;

	private IPartialSCCertificate stagingPSCCertificate = null;

	/**
	 * Default Constructor
	 */
	public OBPartialSCCertificateTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBPartialSCCertificateTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the partial sc certificate busines entity
	 * 
	 * @return ISCCertificate
	 */
	public IPartialSCCertificate getPartialSCCertificate() {
		return this.pscCertificate;
	}

	/**
	 * Get the staging partial sc certificate business entity
	 * 
	 * @return ISCCertificate
	 */
	public IPartialSCCertificate getStagingPartialSCCertificate() {
		return this.stagingPSCCertificate;
	}

	/**
	 * Set the partial sc certificate busines entity
	 * 
	 * @param anIPartialSCCertificate is of type IPartialSCCertificate
	 */
	public void setPartialSCCertificate(IPartialSCCertificate anIPartialSCCertificate) {
		this.pscCertificate = anIPartialSCCertificate;
	}

	/**
	 * Set the staging partial sc certificate business entity
	 * 
	 * @param anIPartialSCCertificate is of type IPartialSCCertificate
	 */
	public void setStagingPartialSCCertificate(IPartialSCCertificate anIPartialSCCertificate) {
		this.stagingPSCCertificate = anIPartialSCCertificate;
	}
}