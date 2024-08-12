/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/IWarehouseTrxValue.java,v 1.4 2004/08/17 06:52:48 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents the transaction value object for Components.
 * 
 * @author $Author: wltan $
 * 
 * @version $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public interface IWarehouseTrxValue extends ICMSTrxValue {
	public IWarehouse[] getWarehouse();

	public IWarehouse[] getStagingWarehouse();

	public void setWarehouse(IWarehouse[] value);

	public void setStagingWarehouse(IWarehouse[] value);

	public void setCountryCode(String countryCode);

	public String getCountryCode();
}
