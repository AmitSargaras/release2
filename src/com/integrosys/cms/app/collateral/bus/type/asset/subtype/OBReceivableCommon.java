/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/OBReceivableCommon.java,v 1.7 2005/08/12 04:39:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.OBAssetBasedCollateral;

/**
 * This class represents a common asset for type Receivables.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/12 04:39:17 $ Tag: $Name: $
 */
public class OBReceivableCommon extends OBAssetBasedCollateral implements IReceivableCommon {
	private String invoiceType;

	private Amount nominalValue;

	/**
	 * Default Constructor.
	 */
	public OBReceivableCommon() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IReceivableCommon
	 */
	public OBReceivableCommon(IReceivableCommon obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get invoice type.
	 * 
	 * @return String
	 */
	public String getInvoiceType() {
		return invoiceType;
	}

	/**
	 * Set invoice type.
	 * 
	 * @param invoiceType is of type String
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * Get nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue() {
		return nominalValue;
	}

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue) {
		this.nominalValue = nominalValue;
	}

	private String remarks;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBReceivableCommon)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}