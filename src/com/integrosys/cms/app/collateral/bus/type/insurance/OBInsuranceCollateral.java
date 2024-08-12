/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/OBInsuranceCollateral.java,v 1.4 2005/09/29 09:38:39 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type Insurance entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/29 09:38:39 $ Tag: $Name: $
 */
public class OBInsuranceCollateral extends OBCollateral implements IInsuranceCollateral {
	private ICDSItem[] cdsItems;

	/**
	 * Default Constructor.
	 */
	public OBInsuranceCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IInsuranceCollateral
	 */
	public OBInsuranceCollateral(IInsuranceCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Return a array of CDS Items
	 * 
	 * @return ICDSItem[]
	 */
	public ICDSItem[] getCdsItems() {
		return this.cdsItems;
	}

	/**
	 * Set CDS Items
	 * 
	 * @parameter cdsItems of type ICDSItem[]
	 */
	public void setCdsItems(ICDSItem[] cdsItems) {
		this.cdsItems = cdsItems;
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
		else if (!(obj instanceof OBInsuranceCollateral)) {
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
	
	private Amount insurancePremium;

	public Amount getInsurancePremium() {
		return insurancePremium;
	}

	public void setInsurancePremium(Amount insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

}