/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/OBWarehouseTrxValue.java,v 1.4 2004/08/17 06:52:48 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This interface represents the transaction value object for Components.
 * 
 * @author $Author: wltan $
 * @version $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public class OBWarehouseTrxValue extends OBCMSTrxValue implements IWarehouseTrxValue {

	private IWarehouse[] warehouse = null;

	private IWarehouse[] stagingWarehouse = null;

	private String countryCode = null;

	public OBWarehouseTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_WAREHOUSE);
	}

	public OBWarehouseTrxValue(IWarehouse[] obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public OBWarehouseTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	public IWarehouse[] getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(IWarehouse[] warehouse) {
		this.warehouse = warehouse;
	}

	public IWarehouse[] getStagingWarehouse() {
		return stagingWarehouse;
	}

	public void setStagingWarehouse(IWarehouse[] stagingWarehouse) {
		this.stagingWarehouse = stagingWarehouse;
	}

	public boolean equals(Object o) {
		return this.toString().equals(o.toString());
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * @return Returns the countryCode.
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
