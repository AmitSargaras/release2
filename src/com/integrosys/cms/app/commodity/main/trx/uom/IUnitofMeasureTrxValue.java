/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/IUnitofMeasureTrxValue.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public interface IUnitofMeasureTrxValue extends ICMSTrxValue {

	/**
	 * Gets list of actual unit of measure contained in this transaction.
	 * 
	 * @return IUnitofMeasure[]
	 */
	public IUnitofMeasure[] getUnitofMeasure();

	/**
	 * Gets list of staging unit of measure contained in this transaction.
	 * 
	 * @return IUnitofMeasure[]
	 */
	public IUnitofMeasure[] getStagingUnitofMeasure();

	/**
	 * Sets the actual unit of measure for this transaction.
	 * 
	 * @param aValue - IUnitofMeasure[]
	 */
	public void setUnitofMeasure(IUnitofMeasure[] aValue);

	/**
	 * Sets the staging unit of measure for this transaction.
	 * 
	 * @param aValue - IUnitofMeasure[]
	 */
	public void setStagingUnitofMeasure(IUnitofMeasure[] aValue);

	/**
	 * Get commodity category code.
	 * 
	 * @return String
	 */
	public String getCategoryCode();

	/**
	 * Set commodity category code.
	 * 
	 * @param categoryCode of type String
	 */
	public void setCategoryCode(String categoryCode);

	/**
	 * Get commodity product type code.
	 * 
	 * @return String
	 */
	public String getProductTypeCode();

	/**
	 * Set commodity product type code.
	 * 
	 * @param typeCode of type String
	 */
	public void setProductTypeCode(String typeCode);
}
