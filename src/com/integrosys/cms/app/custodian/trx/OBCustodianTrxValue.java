/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/trx/OBCustodianTrxValue.java,v 1.6 2005/04/08 06:31:13 wltan Exp $
 */
package com.integrosys.cms.app.custodian.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.OBCustodianDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Implementation class for the ICustodianDocTrxValue interface
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/04/08 06:31:13 $ Tag: $Name: $
 */
public class OBCustodianTrxValue extends OBCMSTrxValue implements ICustodianTrxValue {
	private OBCustodianDoc stagingCustodianDoc;

	private OBCustodianDoc custodianDoc;

	public OBCustodianTrxValue() {
		super();
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBCustodianTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the staging custodian doc
	 * @return OBCustodianDoc - the object containing the staging custodian doc
	 *         info
	 */
	public ICustodianDoc getStagingCustodianDoc() {
		return this.stagingCustodianDoc;
	}

	/**
	 * Get the actual custodian doc
	 * @return OBCustodianDoc - the object containing the actual custodian doc
	 *         info
	 */
	public ICustodianDoc getCustodianDoc() {
		return this.custodianDoc;
	}

	/**
	 * Set the staging custodian doc
	 * @param anICustodianDoc - ICustodianDoc
	 */
	public void setStagingCustodianDoc(ICustodianDoc anICustodianDoc) {
		this.stagingCustodianDoc = (OBCustodianDoc) anICustodianDoc;
	}

	/**
	 * Set the actual custodian doc
	 * @param anICustodianDoc - ICustodianDoc
	 */
	public void setCustodianDoc(ICustodianDoc anICustodianDoc) {
		this.custodianDoc = (OBCustodianDoc) anICustodianDoc;
	}
}
