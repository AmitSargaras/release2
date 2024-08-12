/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/OBSCCertificateTrxValue.java,v 1.1 2003/08/08 12:45:37 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for ISCCertificateTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:45:37 $ Tag: $Name: $
 */
public class OBSCCertificateTrxValue extends OBCMSTrxValue implements ISCCertificateTrxValue {
	private ISCCertificate scCertificate = null;

	private ISCCertificate stagingSCCertificate = null;

	/**
	 * Default Constructor
	 */
	public OBSCCertificateTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBSCCertificateTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the sc certificate busines entity
	 * 
	 * @return ISCCertificate
	 */
	public ISCCertificate getSCCertificate() {
		return this.scCertificate;
	}

	/**
	 * Get the staging sc certificate business entity
	 * 
	 * @return ISCCertificate
	 */
	public ISCCertificate getStagingSCCertificate() {
		return this.stagingSCCertificate;
	}

	/**
	 * Set the sc certificate busines entity
	 * 
	 * @param anISCCertificate is of type ISCCertificate
	 */
	public void setSCCertificate(ISCCertificate anISCCertificate) {
		this.scCertificate = anISCCertificate;
	}

	/**
	 * Set the staging sc certificate business entity
	 * 
	 * @param anISCCertificate is of type ISCCertificate
	 */
	public void setStagingSCCertificate(ISCCertificate anISCCertificate) {
		this.stagingSCCertificate = anISCCertificate;
	}
}